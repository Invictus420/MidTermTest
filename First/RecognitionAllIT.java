/*
 * Copyright 2013 JavaANPR contributors
 * Copyright 2006 Ondrej Martinsky
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private static final int currentlyCorrectSnapshots = 53;
    private static final Logger logger = LoggerFactory.getLogger(RecognitionIT.class);
    private File snap;


    public RecognitionAllIT(File data) throws Exception {
        this.snap = data;
    }

    @Parameterized.Parameters
    public static File[] data() {

        String snapshotDirPath = "src/test/resources/snapshots";
        File snapshotDir = new File(snapshotDirPath);
        File[] snapshots = snapshotDir.listFiles();
        return snapshots;
    }

    //    TODO 3 Fix for some strange encodings of jpeg images - they don't always load correctly
    //    See: http://stackoverflow.com/questions/2408613/problem-reading-jpeg-image-using-imageio-readfile-file
    //    B/W images load without a problem: for now - using snapshots/test_041.jpg

    @Test
    public void tstAl() throws IOException, ParserConfigurationException, SAXException {
        logger.info("####### RUNNING: NAME_OF_TEST ######");
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));
        Properties properties = new Properties();
        properties.load(resultsStream);

        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(this.snap));
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());


        String name = this.snap.getName();
        String plateCorrect = properties.getProperty(name);

        Intelligence intel = new Intelligence();
        assertNotNull(intel);

        String numberPlate = intel.recognize(carSnap, false);
        assertNotNull("The licence plate is null - are you sure the image has the correct color space?", numberPlate);


        assertEquals(plateCorrect, numberPlate);

        carSnap.close();
    }
}