package com.erasko.lns2.loggers;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class StaxStreamGameLogger extends GameLogger {

    private XMLOutputFactory factory;
    private XMLStreamWriter writer;

    private XMLStreamReader reader;
    private XMLInputFactory inputFactory;
    private FileReader fileReader;

    // Метод достает из allData данные и обработав записывает их в xml-файл
    @Override
    protected void saveDataInFile() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        date = new Date();
        String dateSuffix = dateFormat.format(date);
        // Собираем имя файла, добавили временное значение
        String file = firstPartOfFile + dateSuffix + ".xml";
        currentNewRecordedFile = file;

        try {
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
        } catch (FactoryConfigurationError | XMLStreamException e) {
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
    @Override
    public void readFile(String fileName) {

        //Вспомогательный набор, хранит распарсенную информацию
        helpField = new int[3][3];
        helpPayerList = new ArrayList<>(2);
        playerStepArray = new ArrayList<>(9);
        winnerOrDraw = new StringBuilder();

        try {
            fileReader = new FileReader(fileName);
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
                            helpPayerList.add(plNumber + " игрок: " + plName + ", ходит " + symbol);
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

