package br.com.hevermc.commons.bungee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	public static String getDate() {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		Calendar calendar = GregorianCalendar.getInstance(tz);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(calendar.getTime());
	}

	private static String fromLong(long lenth) {
		int days = (int) TimeUnit.SECONDS.toDays(lenth);
		long hours = TimeUnit.SECONDS.toHours(lenth) - (days * 24);
		long minutes = TimeUnit.SECONDS.toMinutes(lenth) - TimeUnit.SECONDS.toHours(lenth) * 60L;
		long seconds = TimeUnit.SECONDS.toSeconds(lenth) - TimeUnit.SECONDS.toMinutes(lenth) * 60L;
		String totalDay = String.valueOf(days) + ((days == 1) ? " dia " : " dias ");
		String totalHours = String.valueOf(hours) + ((hours == 1L) ? " hora " : " horas ");
		String totalMinutes = String.valueOf(minutes) + ((minutes == 1L) ? " minuto " : " minutos ");
		String totalSeconds = String.valueOf(seconds) + ((seconds == 1L) ? " segundo" : " segundos");
		if (days == 0)
			totalDay = "";
		if (hours == 0L)
			totalHours = "";
		if (minutes == 0L)
			totalMinutes = "";
		if (seconds == 0L)
			totalSeconds = "";
		String restingTime = String.valueOf(totalDay) + totalHours + totalMinutes + totalSeconds;
		restingTime = restingTime.trim();
		if (restingTime.equals(""))
			restingTime = "0 segundos";
		return restingTime;
	}

	public static String formatDifference(long time) {
		long totalLenth = time;
		long timeLefting = totalLenth - System.currentTimeMillis();
		long seconds = timeLefting / 1000L;
		return fromLong(seconds);
	}

	public static long parseDateDiff(String time, boolean future) throws Exception {
		Pattern timePattern = Pattern.compile(
				"(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?",

				2);
		Matcher m = timePattern.matcher(time);
		int years = 0;
		int months = 0;
		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		boolean found = false;
		while (m.find()) {
			if (m.group() == null || m.group().isEmpty())
				continue;
			for (int i = 0; i < m.groupCount(); i++) {
				if (m.group(i) != null && !m.group(i).isEmpty()) {
					found = true;
					break;
				}
			}
			if (found) {
				if (m.group(1) != null && !m.group(1).isEmpty())
					years = Integer.parseInt(m.group(1));
				if (m.group(2) != null && !m.group(2).isEmpty())
					months = Integer.parseInt(m.group(2));
				if (m.group(3) != null && !m.group(3).isEmpty())
					weeks = Integer.parseInt(m.group(3));
				if (m.group(4) != null && !m.group(4).isEmpty())
					days = Integer.parseInt(m.group(4));
				if (m.group(5) != null && !m.group(5).isEmpty())
					hours = Integer.parseInt(m.group(5));
				if (m.group(6) != null && !m.group(6).isEmpty())
					minutes = Integer.parseInt(m.group(6));
				if (m.group(7) != null && !m.group(7).isEmpty())
					seconds = Integer.parseInt(m.group(7));
				break;
			}
		}
		if (!found)
			throw new Exception("Illegal Date");
		if (years > 20)
			throw new Exception("Illegal Date");
		Calendar c = new GregorianCalendar();
		if (years > 0)
			c.add(1, years * (future ? 1 : -1));
		if (months > 0)
			c.add(2, months * (future ? 1 : -1));
		if (weeks > 0)
			c.add(3, weeks * (future ? 1 : -1));
		if (days > 0)
			c.add(5, days * (future ? 1 : -1));
		if (hours > 0)
			c.add(11, hours * (future ? 1 : -1));
		if (minutes > 0)
			c.add(12, minutes * (future ? 1 : -1));
		if (seconds > 0)
			c.add(13, seconds * (future ? 1 : -1));
		return c.getTimeInMillis();
	}
}