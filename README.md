# kandilli4j
A tiny api to read latest earthquakes from Kandilli Observatory Earthquake Research Institute (Kandilli Rasathanesi)

Kandilli Rasathanesi sayfasından son depremleri listeleyen mini api.


#usage

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
