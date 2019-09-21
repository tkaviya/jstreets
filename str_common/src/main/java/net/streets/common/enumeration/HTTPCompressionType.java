package net.streets.common.enumeration;

import java.util.Arrays;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;

/******************************************************************************
 * *
 * Created:     27 / 10 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 ******************************************************************************/


public enum HTTPCompressionType {

    GZIP {
        public String value() {
            return "gzip";
        }
    },
    ZLIB {
        public String value() {
            return "zlib";
        }
    },
    X_COMPRESS {
        public String value() {
            return "x-compress";
        }
    },
    X_ZIP {
        public String value() {
            return "x-zip";
        }
    };

    public static boolean isCompressedEncodingType(String contentStr) {
        return !isNullOrEmpty(contentStr) && Arrays.stream(HTTPCompressionType.values())
                .filter((t) -> contentStr.toLowerCase().contains(t.name().toLowerCase()) ||
                        contentStr.toLowerCase().contains(t.value().toLowerCase()))
                .count() > 0;
    }

    public abstract String value();
}
