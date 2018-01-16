package com.vehicle.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


public class MyBatisGeneratorSample {
	
	public static void main(String[] args) {
		
		List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config;
        MyBatisGenerator myBatisGenerator = null;
        
		try {
			config = cp.parseConfiguration(MyBatisGeneratorSample.class.getResourceAsStream("GeneratorXmlConfig.xml"));
			DefaultShellCallback shellCallback = new DefaultShellCallback(true);
			myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
			myBatisGenerator.generate(null);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
