package io.helidon.examples.quickstart.mp;

import javax.enterprise.context.Dependent;

@Dependent
public class Foo {

	public String getValue() {
		return "foo";
	}
	
}
