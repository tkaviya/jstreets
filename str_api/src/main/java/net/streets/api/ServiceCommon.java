package net.streets.api;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public class ServiceCommon {
    public static <T> T getRealParamValue(T param) {
        return isNullOrEmpty(String.valueOf(param)) ? null : param;
    }
}
