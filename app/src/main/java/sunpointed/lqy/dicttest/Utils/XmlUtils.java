package sunpointed.lqy.dicttest.Utils;

import android.util.Log;
import android.util.SparseArray;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import sunpointed.lqy.dicttest.bean.DictItemBean;

/**
 * Created by lqy on 16/5/26.
 */
public class XmlUtils {

    public static final String TAG = "XmlUtils";
    static SAXParserFactory factory = SAXParserFactory.newInstance();

    public static SparseArray<DictItemBean> fromXml(String data) {
        SparseArray<DictItemBean> array = new SparseArray<>();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            SAXHandler handler = new SAXHandler(array);
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new ByteArrayInputStream(data.getBytes())));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static class SAXHandler extends DefaultHandler {
        private String tagName = null;
        private DictItemBean currentItem = null;
        private SparseArray<DictItemBean> mItems;
        private int mSize;
        private int[] mCount = {0, 0};

        public SAXHandler(SparseArray<DictItemBean> items){
            super();
            mItems = items;
            mSize = 0;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if (localName.equals("yourword")) {
                currentItem = new DictItemBean();
            }
            this.tagName = localName;
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if(localName.equals("yourword")){
                mItems.put(mSize, currentItem);
                mSize++;
            }
            tagName = null;
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (tagName != null) {
                String data = new String(ch, start, length).trim();
                if (tagName.equals("subimg")) {
                    currentItem.subimg = data;
                } else if (tagName.equals("suben")) {
                    currentItem.suben = data;
                } else if (tagName.equals("subcn")) {
                    currentItem.subcn = data;
                } else if (tagName.equals("keyword")) {
                    currentItem.keyword = data;
                } else if (tagName.equals("yinbiao")) {
                    if(mCount[0] == 0) {
                        currentItem.yinbiao = data;
                        mCount[0]++;
                    }else if(mCount[0] == 1){
                        currentItem.yinbiao += data;
                    }
                } else if (tagName.equals("explain")) {
                    mCount[0] = 0;
                    if(mCount[1] == 0) {
                        currentItem.explain = data;
                        mCount[1]++;
                    }else if(mCount[1] == 1){
                        currentItem.explain += data;
                    }
                } else if (tagName.equals("subaudio")) {
                    mCount[1] = 0;
                    currentItem.subaudio = data;
                } else if (tagName.equals("wordaudio")) {
                    currentItem.wordaudio = data;
                } else if (tagName.equals("subid")) {
                    currentItem.subid = Integer.valueOf(data);
                } else if (tagName.equals("sid")) {
                    currentItem.sid = Integer.valueOf(data);
                } else if (tagName.equals("filmname")) {
                    currentItem.filmname = data;
                } else if (tagName.equals("filmid")) {
                    currentItem.filmid = data;
                }
            }
        }
    }
}
