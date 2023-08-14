package com.sz.core.io;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

public class ClassPathXmlResource implements Resource {


    private Element rootElement;
    private Iterator<Element> elementIterator;

    private Document document;


    public ClassPathXmlResource(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        rootElement = document.getRootElement();
        elementIterator = rootElement.elementIterator();
    }

    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return elementIterator.next();
    }
}
