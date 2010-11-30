/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 27 juil. 2010
 */

package uk.icat3.restriction;

import uk.icat3.restriction.sample.SampleTest;
import uk.icat3.restriction.investigation.InvestigationTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import uk.icat3.restriction.exception.RestrictionExceptionTest;

/**
 *
 * @author cruzcruz
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DatasetTest.class,
    InvestigationTest.class,
    SampleTest.class,
    DatafileTest.class,
    RestrictionExceptionTest.class,
    UsesExamples.class
})
        
public class TestAllRestrictions {
     public static Test suite() {
        return new JUnit4TestAdapter(TestAllRestrictions.class);
    }
}