/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.apache.juli.logging.jdk14;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.juli.logging.PathableClassLoader;
import org.apache.juli.logging.PathableTestSuite;


/**
 * <p>TestCase for JDK 1.4 logging when running on a JDK 1.4 system with
 * zero configuration, and with Log4J not present (so JDK 1.4 logging
 * should be automatically configured.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 427808 $ $Date: 2006-08-02 02:08:20 +0200 (on, 02 aug 2006) $
 */

public class DefaultConfigTestCase extends TestCase {


    // ----------------------------------------------------------- Constructors


    /**
     * <p>Construct a new instance of this test case.</p>
     *
     * @param name Name of the test case
     */
    public DefaultConfigTestCase(String name) {
        super(name);
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * <p>The {@link LogFactory} implementation we have selected.</p>
     */
    protected LogFactory factory = null;


    /**
     * <p>The {@link Log} implementation we have selected.</p>
     */
    protected Log log = null;


    // ------------------------------------------- JUnit Infrastructure Methods


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        setUpFactory();
        setUpLog("TestLogger");
    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() throws Exception {
        PathableClassLoader loader = new PathableClassLoader(null);
        loader.useExplicitLoader("junit.", Test.class.getClassLoader());
        loader.addLogicalLib("testclasses");
        loader.addLogicalLib("commons-logging");
        
        Class testClass = loader.loadClass(DefaultConfigTestCase.class.getName());
        return new PathableTestSuite(testClass, loader);
    }

    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        log = null;
        factory = null;
        LogFactory.releaseAll();
    }


    // ----------------------------------------------------------- Test Methods


    // Test pristine Log instance
    public void testPristineLog() {

        checkLog();

    }


    // Test pristine LogFactory instance
    public void testPristineFactory() {

        assertNotNull("LogFactory exists", factory);
        assertEquals("LogFactory class",
                     "org.apache.juli.logging.impl.LogFactoryImpl",
                     factory.getClass().getName());

        String names[] = factory.getAttributeNames();
        assertNotNull("Names exists", names);
        assertEquals("Names empty", 0, names.length);

    }


    // Test Serializability of Log instance
    public void testSerializable() throws Exception {

        // Serialize and deserialize the instance
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(log);
        oos.close();
        ByteArrayInputStream bais =
            new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        log = (Log) ois.readObject();
        ois.close();

        // Check the characteristics of the resulting object
        checkLog();

    }


    // -------------------------------------------------------- Support Methods



    // Check the log instance
    protected void checkLog() {

        assertNotNull("Log exists", log);
        assertEquals("Log class",
                     "org.apache.juli.logging.impl.Jdk14Logger",
                     log.getClass().getName());

        // Can we call level checkers with no exceptions?
        log.isDebugEnabled();
        log.isErrorEnabled();
        log.isFatalEnabled();
        log.isInfoEnabled();
        log.isTraceEnabled();
        log.isWarnEnabled();

    }


    // Set up factory instance
    protected void setUpFactory() throws Exception {
        factory = LogFactory.getFactory();
    }


    // Set up log instance
    protected void setUpLog(String name) throws Exception {
        log = LogFactory.getLog(name);
    }


}
