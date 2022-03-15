package com.hyperpl.kandilli4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.hyperpl.kandilli4j.exception.ParseEarthquakeException;
import com.hyperpl.kandilli4j.exception.ReadEarthquakeException;

public class EarthquakeListProvider {

	private String mPageURL;
	private boolean mVerbose = false;

	public EarthquakeListProvider() {
		this(EarthquakeProviderConstants.DEFAULT_URL);
	}

	public EarthquakeListProvider(String pPageURL) {
		this.mPageURL = pPageURL;
	}

	public Vector<EarthquakeInfo> getLastEarthquakes(int pNumOfLastEarthquakes) throws ReadEarthquakeException, ParseEarthquakeException {
		return this.getLastEarthquakes(pNumOfLastEarthquakes, 0.0);
	}

	public Vector<EarthquakeInfo> getLastEarthquakes() throws ReadEarthquakeException, ParseEarthquakeException {
		return this.getLastEarthquakes(Integer.MAX_VALUE, 0.0);
	}

	public Vector<EarthquakeInfo> getLastEarthquakes(double pMinMagnitude) throws ReadEarthquakeException, ParseEarthquakeException {
		return this.getLastEarthquakes(Integer.MAX_VALUE, pMinMagnitude);
	}

	public Vector<EarthquakeInfo> getLastEarthquakes(int pNumOfLastEarthquakes, double pMinMagnitude) throws ReadEarthquakeException, ParseEarthquakeException {

		Vector<EarthquakeInfo> vEarthquakeList = new Vector<EarthquakeInfo>();

		String strHTTPResponse = this.sendPost();

		try {
			int nIndex = strHTTPResponse.indexOf("<pre>") + 5;
			int nLastIndex = strHTTPResponse.indexOf("</pre>");

			// get <pre> .. </pre>
			strHTTPResponse = strHTTPResponse.substring(nIndex, nLastIndex).trim();

			strHTTPResponse = strHTTPResponse.substring(strHTTPResponse.lastIndexOf("------") + 6).trim();

			String[] strLines = strHTTPResponse.split("\n");

			DateFormat formatter = new SimpleDateFormat(EarthquakeProviderConstants.DATE_FORMAT_ONSOURCE);

			for (int i = 0; i < strLines.length && vEarthquakeList.size() < pNumOfLastEarthquakes; i++) {

				String strLine = strLines[i];

				try {

					// read date
					int nTmpIndex = strLine.indexOf("  ");
					String strDate = strLine.substring(0, nTmpIndex);
					Date dDate = formatter.parse(strDate);
					strLine = strLine.substring(nTmpIndex + 1).trim();

					// read latitude
					nTmpIndex = strLine.indexOf("  ");
					double nLatitude = Double.parseDouble(strLine.substring(0, nTmpIndex));
					strLine = strLine.substring(nTmpIndex + 1).trim();

					// read longitude
					nTmpIndex = strLine.indexOf("  ");
					double nLongitude = Double.parseDouble(strLine.substring(0, nTmpIndex));
					strLine = strLine.substring(nTmpIndex + 1).trim();

					nTmpIndex = strLine.indexOf(" ");
					double nDepth = Double.parseDouble(strLine.substring(0, nTmpIndex));
					strLine = strLine.substring(nTmpIndex + 1).trim();

					// clear magnitude 1
					nTmpIndex = strLine.indexOf(" ");
					strLine = strLine.substring(nTmpIndex + 1).trim();

					// read magnitude
					nTmpIndex = strLine.indexOf(" ");
					double nMagnitude = 0.0;

					try {
						nMagnitude = Double.parseDouble(strLine.substring(0, nTmpIndex));
					} catch (NumberFormatException ex) {
						printlog("can not read magnitude line:" + strLines[i]);
					}
					strLine = strLine.substring(nTmpIndex + 1).trim();

					if (nMagnitude >= pMinMagnitude) {
						// clear magnitude 3
						nTmpIndex = strLine.indexOf(" ");
						strLine = strLine.substring(nTmpIndex + 1).trim();

						// read place
						nTmpIndex = strLine.indexOf("   ");
						String strPlace = strLine.substring(0, nTmpIndex);
						strLine = strLine.substring(nTmpIndex + 1).trim();

						String strNitelik = strLine.trim();

						EarthquakeInfo iEarthquake = new EarthquakeInfo(dDate, nLatitude, nLongitude, nDepth, nMagnitude, strPlace, strNitelik);
						vEarthquakeList.add(iEarthquake);
					}
				} catch (Exception e) {
					//System.out.println(i + "parsing" + strLine);
					//do nothing intentionally for temp
				}
			}
		} catch (Exception e) {
			throw new ParseEarthquakeException(e);
		}

		return vEarthquakeList;
	}

	private String sendPost() throws ReadEarthquakeException {

		try {

			URL obj = new URL(this.mPageURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", EarthquakeProviderConstants.USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			this.printlog("Sending post request to ", this.mPageURL);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes("");
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			this.printlog("Response code: ", responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine).append("\n");
			}
			in.close();

			return response.toString();
		} catch (Exception e) {
			throw new ReadEarthquakeException(e);
		}
	}

	private void printlog(Object... args) {

		if (mVerbose) {
			for (Object string : args) {
				System.out.print(string);
				System.out.print(" ");
			}

			System.out.println();
		}

	}

}
