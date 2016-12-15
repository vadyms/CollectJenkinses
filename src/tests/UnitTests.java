package tests;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Jenkins;
import models.Job;
import services.ProcessJenkins;
import services.ProcessJob;
import services.Start;

public class UnitTests {
	
	Jenkins jenkinsTest;
	Job jobTest;
	ProcessJob processJob;
	ProcessJenkins processJenkins;
	String jenkinsURL = "http://1.9.97.163:8080/";
	String someJob = "Auto Deployment - OOTB 1.9.97.177";
	String someJobURL = "http://1.9.97.163:8080/job/Auto%20Deployment%20-%20OOTB%201.9.97.177/";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		jenkinsTest = new Jenkins();
		jenkinsTest.setJenkinsUrl(jenkinsURL);
		jenkinsTest.setUserName("");
		jenkinsTest.setUserPassword("");
		
		jobTest = new Job();
		jobTest.setName(someJob);
		jobTest.setUrl(someJobURL);
		
		processJob = new ProcessJob();
		processJob.setJob(jobTest);
		processJob.setJenkins(jenkinsTest);
		
		processJenkins=new ProcessJenkins();
		processJenkins.setJenkins(jenkinsTest);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStart() {
		try {
			Start.main(new String[]{".\\..\\CollectJenkins\\WebContent\\Data\\"});
		} catch (Exception e) {
			fail("Exception happens.");
		}
	}

	@Test
	public void testAnalyzeJob() {
		
		try {
			processJob.analyseJob();
		} catch (Exception e) {
			System.err.println(e);
			fail("Exception happens.");
		}
	}

	@Test
	public void testAnalyzeJobRun() {
		
		try {
			processJob.run();
		} catch (Exception e) {
			System.err.println(e);
			fail("Exception happens.");
		}
	}
	
	@Test
	public void testProcessJenkins() {
		try {
			processJenkins.run();
		} catch (Exception e) {
			System.err.println(e);
			fail("Exception happens.");
		}
	}
}
