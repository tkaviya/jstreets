package net.streets.common.utilities;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import java.io.File;
import java.util.logging.Logger;

import static java.lang.String.format;

/***************************************************************************
 * Created:     19 / 04 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class FileUtils {

    private static Logger logger = Logger.getLogger(FileUtils.class.getSimpleName());

    public static String getMimeType(final File file) {
        if (file == null) return null;
        logger.info("Getting MIME type of file " + file.getAbsolutePath());
        String type;
        try {
            final MagicMatch match = Magic.getMagicMatch(file, true, false);
            type = match.getMimeType();
            logger.info(format("Got MIME type '%s' from file contents", type));
        } catch (Exception ex) {
            logger.severe("Error while determining MIME type: " + ex.getMessage());
            type = MimeTypeConstants.getMimeTypeByFileName(file.getName());
            logger.info(format("Guessed MIME type '%s' based on filename", type));
        }
        if (type == null || type.equals("???")) {
            logger.warning(format("Could not get valid MIME type (%s). Returning null type", type));
            return null;
        }
        return type;
    }
}
