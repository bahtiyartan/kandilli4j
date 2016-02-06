package com.yedibit.kandilli4j.test;

import java.util.Vector;

import com.yedibit.kandilli4j.EarthquakeInfo;
import com.yedibit.kandilli4j.EarthquakeListProvider;
import com.yedibit.kandilli4j.exception.ParseEarthquakeException;
import com.yedibit.kandilli4j.exception.ReadEarthquakeException;

public class TestKandilli4j {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		EarthquakeListProvider iProvider = new EarthquakeListProvider();

		try {
			// reads all earthquakes
			Vector<EarthquakeInfo> all = iProvider.getLastEarthquakes();

			// reads last ten earthquakes
			Vector<EarthquakeInfo> lastten = iProvider.getLastEarthquakes(10);

			// reads all earthquakes greater than 4.0 (Ml)
			Vector<EarthquakeInfo> allGreaterThan4Ml = iProvider.getLastEarthquakes(4.0);

			// reads last ten earthquakes greater than 4.0 (Ml)
			Vector<EarthquakeInfo> lastTenGreaterThan4Ml = iProvider.getLastEarthquakes(10, 4.0);

			for (EarthquakeInfo iInfo : lastTenGreaterThan4Ml) {
				System.out.println(iInfo.toString());
			}

		} catch (ReadEarthquakeException e) {
			System.out.println("can not read earthquake information");
			e.printStackTrace();
		} catch (ParseEarthquakeException e) {
			System.out.println("can not parse earthquake information");
			e.printStackTrace();
		}

	}

}
