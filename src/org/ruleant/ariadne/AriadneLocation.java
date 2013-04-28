/**
 * Custom Ariadne Location object
 *
 * Copyright (C) 2012-2013 Dieter Adriaenssens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @package org.ruleant.ariadne
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
package org.ruleant.ariadne;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.location.Location;

/**
 * Custom Ariadne Location object, it inherits from Location class,
 * but overrides the getString() method.
 *
 * @author  Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class AriadneLocation extends Location {

    /**
     * Constructor.
     *
     * @param provider Provider name
     */
    public AriadneLocation(final String provider) {
        super(provider);
    }

    /**
     * Constructor.
     *
     * @param location Location object
     */
    public AriadneLocation(final Location location) {
        super(location);
    }

    /**
     * Checks if the timestamp of the provided location
     * is more recent than this location.
     *
     * @param Location object
     * @return true if location is more recent than this location
     */
    public boolean isNewer(final Location location) {
        return (location.getTime() > super.getTime());
    }

    /**
     * Overrides the toString() method, implementing a formatted String.
     *
     * @param context Context of the App
     * @return String formatted string
     */
    public String toString(final Context context) {
        String locationText = "";

        // Format location
        locationText += " "
            + context.getResources().getString(R.string.latitude) + ": "
            + getLatitude() + "°\n";
        locationText += " "
            + context.getResources().getString(R.string.longitude) + ": "
            + getLongitude() + "°\n";
        if (hasAltitude()) {
            locationText += " "
                + context.getResources().getString(R.string.altitude) + ": "
                + FormatUtils.formatDist(getAltitude()) + "\n";
        }
        if (hasBearing()) {
            locationText += " "
                + context.getResources().getString(R.string.bearing) + ": "
                + getBearing() + "°\n";
        }
        if (hasSpeed()) {
            locationText += " "
                + context.getResources().getString(R.string.speed) + ": "
                + FormatUtils.formatSpeed(getSpeed()) + "\n";
        }
        if (hasAccuracy()) {
            locationText += " "
                + context.getResources().getString(R.string.accuracy) + ": "
                + FormatUtils.formatDist(getAccuracy()) + "\n";
        }

        // Location provider
        if (!getProvider().isEmpty()) {
            locationText += " "
                + context.getResources().getString(R.string.provider) + ": "
                + getProvider() + "\n";
        }

        // Format Timestamp
        if (getTime() > 0) {
            Date date = new Date(getTime());
            SimpleDateFormat formatter
                = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSz");
            locationText += " "
                + context.getResources().getString(R.string.timestamp) + ": "
                + formatter.format(date);
        }

        // Display raw when in debug mode
        DebugLevel debug = new DebugLevel(context);
        if (debug.checkDebugLevel(DebugLevel.DEBUG_LEVEL_HIGH)) {
            locationText += "\n\n "
                + context.getResources().getString(R.string.raw) + ": "
                + super.toString();
        }

        return locationText;
    }
}
