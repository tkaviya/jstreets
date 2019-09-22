package net.streets.persistence.enumeration;

public enum StrChannel {
    DESKTOP, WEB, SMART_PHONE;

    public static StrChannel fromString(String channel) {
        for (StrChannel channelEnum : StrChannel.values()) {
            if (channelEnum.name().equals(channel.toUpperCase())) {
                return channelEnum;
            }
        }
        return null;
    }
}
