package com.securefivewave.util;


import org.apache.http.conn.util.InetAddressUtils;

public final class IpAddressValidationUtil {

    private IpAddressValidationUtil() {
        // nothing
    }

    public static boolean validateIPAddress(String ip) {
        return InetAddressUtils.isIPv4Address(ip) && InetAddressUtils.isIPv6Address(ip);
    }
}