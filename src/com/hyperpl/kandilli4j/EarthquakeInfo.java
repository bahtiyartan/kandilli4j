package com.hyperpl.kandilli4j;

import java.util.Date;

public class EarthquakeInfo {
	private Date mDate;
	private double mLatitude;
	private double mLongitude;
	private double mMagnitude;
	private double mDepth;
	private String mLocationName;
	private String mCharacter;

	public EarthquakeInfo() {
		this.mDate = new Date(0);
		this.mLatitude = 0.0;
		this.mLongitude = 0.0;
		this.mDepth = 0.0;
		this.mLocationName = "";
		this.mCharacter = "";
	}

	public EarthquakeInfo(Date pDate, double pLatitude, double pLongitude, double pDepth, double pMagnitude, String pLocationName, String pCharacter) {
		this.mDate = pDate;
		this.mLatitude = pLatitude;
		this.mLongitude = pLongitude;
		this.mDepth = pDepth;
		this.mMagnitude = pMagnitude;
		this.mLocationName = pLocationName;
		this.mCharacter = pCharacter;
	}

	public Date getDate() {
		return this.mDate;
	}

	public double getLatitude() {
		return this.mLatitude;
	}

	public double getLongitude() {
		return this.mLongitude;
	}

	/**
	 * Depth as km
	 * 
	 * @return
	 */
	public double getDepth() {
		return this.mDepth;
	}

	/**
	 * Magnitude as Ml.
	 * 
	 * If ml value is not defined, returns 0.0
	 * 
	 * @return
	 */
	public double getMagnitude() {
		return this.mMagnitude;
	}

	public String getLocationName() {
		return this.mLocationName;
	}

	public String getCharacter() {
		return this.mCharacter;
	}

	@Override
	public String toString() {
		return mDate + " | " + mLatitude + " | " + mLongitude + " | " + mDepth + " | " + mMagnitude + " | " + mLocationName + " | " + mCharacter;
	}

}