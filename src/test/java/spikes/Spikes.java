package spikes;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * Created by f.barbano on 19/05/2018.
 */
public class Spikes {

	@Test
	public void tempTest() {
		Stream.iterate(4, integer -> integer+1).limit(20).forEach(System.out::println);
	}
}
