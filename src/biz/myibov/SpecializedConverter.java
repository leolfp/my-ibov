package biz.myibov;

import com.blackbear.flatworm.ConversionOption;
import com.blackbear.flatworm.Util;
import com.blackbear.flatworm.errors.FlatwormConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by leonardo on 4/13/14.
 */
public class SpecializedConverter {
    private static Log log = LogFactory.getLog(SpecializedConverter.class);


    /**
     * Conversion function for <code>BigDecimal</code>, returns the source string with padding removed if requested,
     * converted into a big decimal. <p/> In addition to the standard conversion options, big decimals also support the
     * following: <p/>
     * <dl>
     * <dt><code>decimal-implied</code></dt>
     * <dd>If set to <code>true</code>, the decimal point is positionally implied rather than explicitly included.
     * If set, <code>decimal-places</code> is required.</dd>
     * <dt><code>decimal-places</code></dt>
     * <dd>The number of digits in the string which are to the right of the decimal point, if
     * <code>decimal-implied</code> is set.</dd>
     * </dl>
     *
     * @param str The source string
     * @param options The conversion-option values for the field
     * @return The converted big decimal value
     * @throws com.blackbear.flatworm.errors.FlatwormConversionException If the source number fails to parse as a big decimal or the decimal places
     *             option fails to parse as an integer value.
     */

    public BigDecimal convertBigDecimal(String str, Map<String, ConversionOption> options) throws FlatwormConversionException
    {
        try
        {
            int decimalPlaces = 0;
            String decimalPlacesOption = (String) Util.getValue(options, "decimal-places");
            boolean decimalImplied = "true".equals(Util.getValue(options, "decimal-implied"));

            if (decimalPlacesOption != null)
                decimalPlaces = Integer.parseInt(decimalPlacesOption);

            if (str.length() == 0)
                str = "0";

            BigDecimal ret = new BigDecimal(str);

            if (decimalImplied)
                ret = ret.divide(new BigDecimal("10").pow(decimalPlaces));
            return ret;
        } catch (NumberFormatException ex)
        {
            log.error(ex);
            throw new FlatwormConversionException(str);
        }
    }

    public String convertBigDecimal(Object obj, Map<String, ConversionOption> options)
    {
        if (obj == null)
        {
            return null;
        }
        BigDecimal bd = (BigDecimal) obj;

        int decimalPlaces = 0;
        String decimalPlacesOption = (String) Util.getValue(options, "decimal-places");
        boolean decimalImplied = "true".equals(Util.getValue(options, "decimal-implied"));

        if (decimalPlacesOption != null)
            decimalPlaces = Integer.parseInt(decimalPlacesOption);

        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(!decimalImplied);
        format.setMinimumFractionDigits(decimalPlaces);
        format.setMaximumFractionDigits(decimalPlaces);

        return format.format(bd);
    }

    public BigInteger convertBigInteger(String str, Map<String, ConversionOption> options) throws FlatwormConversionException
    {
        try
        {
            if (str.length() == 0)
                str = "0";

            BigInteger ret = new BigInteger(str);

            return ret;
        } catch (NumberFormatException ex)
        {
            log.error(ex);
            throw new FlatwormConversionException(str);
        }
    }

    public String convertBigInteger(Object obj, Map<String, ConversionOption> options)
    {
        if (obj == null)
        {
            return null;
        }
        BigInteger bd = (BigInteger) obj;

        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(0);

        return format.format(bd);
    }

    /**
     * Conversion function for <code>Short</code>, returns the source string with padding removed if requested,
     * converted into a short.
     *
     * @param str The source string
     * @param options The conversion-option values for the field
     * @return The converted short value
     * @throws FlatwormConversionException If the source number fails to parse as an long value.
     */

    public Short convertShort(String str, Map<String, ConversionOption> options) throws FlatwormConversionException
    {
        try
        {
            if (str.trim().length() == 0)
            {
                return null;
            } else
            {
                return Short.valueOf(str);
            }
        } catch (NumberFormatException ex)
        {
            log.error(ex);
            throw new FlatwormConversionException(str);
        }
    }

    public String convertShort(Object obj, Map<String, ConversionOption> options)
    {
        if (obj == null)
        {
            return null;
        }
        Short l = (Short) obj;
        return Short.toString(l.shortValue());
    }

    /**
     * Conversion function for <code>Integer</code>, returns the source string with padding removed if requested,
     * converted into a short.
     *
     * @param str The source string
     * @param options The conversion-option values for the field
     * @return The converted int value
     * @throws FlatwormConversionException If the source number fails to parse as an long value.
     */

    public Integer convertInteger(String str, Map<String, ConversionOption> options) throws FlatwormConversionException
    {
        try
        {
            if (str.trim().length() == 0)
            {
                return null;
            } else
            {
                return Integer.valueOf(str);
            }
        } catch (NumberFormatException ex)
        {
            log.error(ex);
            throw new FlatwormConversionException(str);
        }
    }

    public String convertInteger(Object obj, Map<String, ConversionOption> options)
    {
        if (obj == null)
        {
            return null;
        }
        Integer l = (Integer) obj;
        return Integer.toString(l.intValue());
    }
}
