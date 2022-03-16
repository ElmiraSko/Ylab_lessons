package com.erasko.lns2.loggers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DOMGameLogger extends GameLogger {

    DocumentBuilderFactory dbf = null;
    DocumentBuilder db  = null;
    Document doc = null;

    // Метод записывает выигрышный результат или ничью
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

    // Служебный метод, записывает в файл
    private void saveDataInFile() {
        date = new Date();
        String dateSuffix = dateFormat.format(date);
        // Собираем имя файла, добавили временное значение
        String file = firstPartOfFile + dateSuffix + ".xml";
        currentNewRecordedFile = file;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element root_el = doc.createElement("Gameplay");
        doc.appendChild(root_el);

        Element player1 = createElementPlayer("1", allData.get(0), "X");
        Element player2 = createElementPlayer("2", allData.get(1), "O");
        root_el.appendChild(player1);
        root_el.appendChild(player2);
        Element game = doc.createElement("Game");
        Element gameResult = doc.createElement("GameResult");

        for (int i = 2; i < allData.size(); i++) {
            String[] data = allData.get(i).split(" ");
            if (data[0].matches("\\d+")) {
                Element step = doc.createElement("Step");
                step.setAttribute("num", String.valueOf(data[0]));
                step.setAttribute("playerId", data[1]);
                step.setTextContent(data[2]);
                game.appendChild(step);
            } else if (data[0].equals("Draw!")) {
                gameResult.setTextContent(data[0]);
            } else {
                Element winnerPlayer = createElementPlayer(data[1], data[0], data[2]);
                gameResult.appendChild(winnerPlayer);
            }
            root_el.appendChild(game);
            root_el.appendChild(gameResult);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(file));
            transformer.transform(source, streamResult);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    // Служебный метод, создает дом-элементы игроков
    private Element createElementPlayer(String id, String name, String symbol) {
        Element player = doc.createElement("Player");
        player.setAttribute("id", id);
        player.setAttribute("name", name);
        player.setAttribute("symbol", symbol);
        return player;
    }

    // Метод для чтения xml файла
    @Override
    public void readXMLFile(String fileName) {

        //Вспомогательный набор, хранит распарсенную информацию
        helpField = new int[3][3];
        playerList = new ArrayList<>(2);
        playerStepArray = new ArrayList<>(9);
        winnerOrDraw = new StringBuilder();

        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Прочитали имена играков
            NodeList plList = doc.getElementsByTagName("Player");
            for (int i = 0; i < plList.getLength() - 1; i++) {
                Node plNode = plList.item(i);
                if (plNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element playerEl = (Element) plNode;
                    String plName = playerEl.getAttribute("name");
                    String plNumber = playerEl.getAttribute("id");
                    String plSymbol = playerEl.getAttribute("symbol");
                    playerList.add(plNumber + " игрок: " + plName + ", ходит " + plSymbol);
                }
            }
            // Прочитали ходы
            NodeList stepList = doc.getElementsByTagName("Step");
            for (int i = 0, stCount = stepList.getLength(); i < stCount; i++) {
                Node stepNode = stepList.item(i);
                if (stepNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element stepEl = (Element) stepNode;
                    int playerId = Integer.parseInt(stepEl.getAttribute("playerId"));
                    String coords = stepEl.getTextContent();
                    writeCoordsInHelpField(coords, playerId);
                }
            }
            // Прочитали результат
            Node gResultNode = doc.getElementsByTagName("GameResult").item(0);
            if(gResultNode.hasChildNodes()) {
                NodeList nodeList = gResultNode.getChildNodes();
                if (nodeList.getLength() > 1) {
                    Node resultNode = nodeList.item(1);
                    if (resultNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element resultElement = (Element) resultNode;
                        winnerOrDraw.append("Player ")
                                .append(resultElement.getAttribute("id"))
                                .append(" -> ")
                                .append(resultElement.getAttribute("name"))
                                .append(" is winner as '")
                                .append(resultElement.getAttribute("symbol"))
                                .append("'!");
                    }
                } else {
                    winnerOrDraw.append(gResultNode.getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
