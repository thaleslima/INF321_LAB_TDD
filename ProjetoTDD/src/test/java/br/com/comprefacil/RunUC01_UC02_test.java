package br.com.comprefacil;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UC01Teste.class, UC02Teste.class })
public class RunUC01_UC02_test {
	public static void main(String[] args) {
	  Result result = JUnitCore.runClasses(UC01Teste.class);
	  for (Failure failure : result.getFailures()) {
	     System.out.println(failure.toString());
	  }
	  System.out.println(result.wasSuccessful());
	}
}
