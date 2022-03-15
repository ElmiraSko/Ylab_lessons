package com.erasko.lns2.loggers;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StaxStreamLogger extends GameLogger {

    FileWriter fileWriter;
    XMLOutputFactory factory;
    XMLStreamWriter writer;

    XMLStreamReader reader;
    XMLInputFactory inputFactory;
    FileReader fileReader;

    // Для временного хранения основных данных
    // получаемых по ходу игры, для дальнейшей обработки и записи в xml-файл
    ArrayList<String> allData;

    // В allData сохраняем имена играков
    @Override
    public void writePlayers(String name1, String name2) {
        count++;
        allData = new ArrayList<>();
        pl1Name = name1;
        pl2Name = name2;
        allData.add(name1);
        allData.add(name2);

        helpField = new int[3][3];
        playerList = new ArrayList<>(2);
        playerStepArray = new ArrayList<>(9);
        winnerOrDraw = new StringBuilder();
    }

    // В allData сохранили ходы играков
    @Override
    public void writeStep(int num, String coords) {
        // определяем playerId
        String playerId = num % 2 == 1 ? "1" : "2";
        StringBuilder step = new StringBuilder()
                .append(num)
                .append(" ")
                .append(playerId)
                .append(" ")
                .append(coords);
        allData.add(step.toString());
    }

    // В allData сохранили результат игры
    @Override
    public void writeWinnerOrDraw(String result) {
        if (result.equals(pl1Name)) {
            allData.add(result + " 1 X");
        } else if (result.equals(pl2Name)) {
            allData.add(result + " 2 O");
        } else {
            allData.add(result);
        }
        saveDataInFile();
    }

    // Метод достает из allData данные и обработав записывает их в xml-файл
    private void saveDataInFile() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Собираем имя файла
        String file = fileName + count + ".xml";

        try {
            fileWriter = new FileWriter(file);
            factory = XMLOutputFactory.newInstance();

            writer = factory.createXMLStreamWriter(out);

            writer.writeStartDocument();
            writer.writeStartElement("Gameplay");

            writer.writeEmptyElement("Player");
            writer.writeAttribute("id", "1");
            writer.writeAttribute("name", allData.get(0));
            writer.writeAttribute("symbol", "X");

            writer.writeEmptyElement("Player");
            writer.writeAttribute("id", "2");
            writer.writeAttribute("name", allData.get(1));
            writer.writeAttribute("symbol", "O");

            writer.writeStartElement("Game");

            for (int i = 2; i < allData.size(); i++) {
                String[] data = allData.get(i).split(" ");
                if (data[0].matches("\\d+")) {
                    writer.writeStartElement("Step");
                    writer.writeAttribute("num", data[0]);
                    writer.writeAttribute("playerId", data[1]);
                    writer.writeCharacters(data[2]);
                } else {
                    writer.writeEndElement(); //   </Game>
                    writer.writeStartElement("GameResult");
                    if (data[0].equals("Draw!")) {
                        writer.writeCharacters(data[0]);
                    } else {
                        writer.writeEmptyElement("Player");
                        writer.writeAttribute("id", data[1]);
                        writer.writeAttribute("name", data[0]);
                        writer.writeAttribute("symbol", data[2]);
                    }
                }
                writer.writeEndElement();  //  </GameResult>
            }
            writer.writeEndElement(); //  </Gameplay>
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (FactoryConfigurationError | XMLStreamException | IOException e) {
            e.printStackTrace();
        }

        String xml = new String(out.toByteArray(), StandardCharsets.UTF_8);
        String prettyPrintXML = formatXML(xml);

        try (FileWriter wr = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(wr)) {
            bw.write(prettyPrintXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatXML(String xml) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, new StreamResult(output));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    // Метод для чтения xml файла
    public void readXMLFile() {
        try {
            fileReader = new FileReader(fileName + count + ".xml");
            inputFactory = XMLInputFactory.newInstance();
            reader = inputFactory.createXMLStreamReader(fileReader);
            int eventType ;

            while (reader.hasNext()) {
                eventType = reader.next();
                // если встречаем стартовый элемент
                if (eventType == XMLEvent.START_ELEMENT) {

                    switch (reader.getName().getLocalPart()) {
                        case "Player":
                            String plNumber = reader.getAttributeValue(null, "id");
                            String plName = reader.getAttributeValue(null, "name");
                            String symbol = reader.getAttributeValue(null, "symbol");
                            playerList.add(plNumber + " игрок: " + plName + ", ходит " + symbol);
                            break;

                        case "Step":
                            String playerId = reader.getAttributeValue(null, "playerId");
                            eventType = reader.next();
                            if (eventType == XMLEvent.CHARACTERS) {
                                String coords = reader.getText();
                                writeCoordsInHelpField(coords, Integer.parseInt(playerId));
                            }
                            break;

                        case "GameResult":
                           reader.next();
                           reader.next();
                            if (reader.getName().getLocalPart().equals("Player")) {
                                String winnerId = reader.getAttributeValue(null, "id");
                                String winnerName = reader.getAttributeValue(null, "name");
                                String winnerSymbol = reader.getAttributeValue(null, "symbol");
                                winnerOrDraw.append("Player ")
                                        .append(winnerId)
                                        .append(" -> ")
                                        .append(winnerName)
                                        .append(" is winner as '")
                                        .append(winnerSymbol)
                                        .append("'!");
                                break;
                            } else {
                                winnerOrDraw.append("Draw!");
                                break;
                            }
                    }
                }
                if (!reader.hasNext())
                    break;
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}

