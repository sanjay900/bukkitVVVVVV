package com.sanjay900.vvvvvv;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LevelParser {

	public static void parseDoc(Location loc) {
		try {

			File fXmlFile = new File(Vvvvvv.getPlugin().getDataFolder().getAbsolutePath()+"/test.vvvvvv");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Data");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Element MetaData = (Element)eElement.getElementsByTagName("MetaData").item(0);
					NodeList levelMetaData = ((Element) (eElement.getElementsByTagName("levelMetaData").item(0))).getElementsByTagName("edLevelClass");
					String Title = MetaData.getElementsByTagName("Title").item(0).getTextContent();
					String Desc1= MetaData.getElementsByTagName("Desc1").item(0).getTextContent();
					String Desc2= MetaData.getElementsByTagName("Desc2").item(0).getTextContent();
					String Desc3= MetaData.getElementsByTagName("Desc3").item(0).getTextContent();
					String website= MetaData.getElementsByTagName("website").item(0).getTextContent();
					int width = Integer.parseInt(eElement.getElementsByTagName("mapwidth").item(0).getTextContent());
					int height = Integer.parseInt(eElement.getElementsByTagName("mapheight").item(0).getTextContent());
					int musicId = Integer.parseInt(eElement.getElementsByTagName("levmusic").item(0).getTextContent());
					String[] levelContentsS = eElement.getElementsByTagName("contents").item(0).getTextContent().substring(0,  eElement.getElementsByTagName("contents").item(0).getTextContent().length()-1).split(",");
					int[] levelTileSets = new int[levelMetaData.getLength()];
					String str = "MetaData: [";
					for (int i = 0; i < levelMetaData.getLength(); i++) {
						str = str+","+String.valueOf(i)+"|"+((Element)levelMetaData.item(i)).getAttribute("tileset");
						levelTileSets[i] = Integer.parseInt(((Element)levelMetaData.item(i)).getAttribute("tileset"));
					}
					System.out.print(str);
					int[] levelContents = new int[levelContentsS.length];
					for (int i = 0; i < levelContentsS.length; i++) {
						levelContents[i] = Integer.parseInt(levelContentsS[i]);
					}
					Map test = new Map(Title, Desc1, Desc2, Desc3, website, width, height, musicId, levelContents, levelTileSets);
					test.debug();
					test.draw(loc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
