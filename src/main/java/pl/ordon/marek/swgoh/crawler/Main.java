package pl.ordon.marek.swgoh.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://swgoh.gg/g/11228/perfekcyjna-niedoskonalosc/").get();
        Elements elements = doc.select("body > div.container.p-t-md > div.content-container > div.content-container-primary.character-list > ul > li.media.list-group-item.p-0.b-t-0 > div > table > tbody > tr > td > a");
        Map<String,Integer> mapa = new HashMap<String, Integer>();
        for (Element element : elements) {
            ArrayList<String> chars = getCharactersByPlayer(element.attributes().get("href"));
            for(String charName: chars){
                if(!mapa.containsKey(charName)){
                    mapa.put(charName,Integer.valueOf(1));
                } else {
                    mapa.replace(charName,Integer.valueOf(mapa.get(charName).intValue()+1));
                }
            }
        }
        System.out.println(mapa);
    }

    private static ArrayList<String> getCharactersByPlayer(String address) throws IOException {
        System.out.println("https://swgoh.gg" + address + "characters/");
        Document doc = Jsoup.connect("https://swgoh.gg" + address + "characters/").get();
        Elements elements = doc.select("body > div.container.p-t-md > div.content-container > div.content-container-primary.character-list > ul > li.media.list-group-item.p-a.collection-char-list > div > div");
        ArrayList<String> characters = new ArrayList<String>();
        for(Element element: elements){
            Elements character = element.select("div > div.collection-char-name > a");
            String charName = character.get(0).text();
            Elements fullChar = element.getElementsByClass("star star7");
            if(!fullChar.isEmpty()){
                characters.add(charName);
            }
        }
        return characters;
    }
}
