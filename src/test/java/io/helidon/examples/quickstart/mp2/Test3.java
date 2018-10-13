package io.helidon.examples.quickstart.mp2;

import javax.inject.Inject;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(WeldJunit5Extension.class)
class Test3 {

    @Inject
    MyBean myBean;

    @Test
    public void testFoo(MyOtherBean otherBean) {
      // Weld SE container is bootstrapped here and the injection points are resolved
    }
}
