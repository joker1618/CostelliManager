package it.costelli.manager.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by f.barbano on 29/05/2017.
 */
public class Converter {

	/* Conversions between data structures types */

	public static <T> TreeSet<T> arrayToTreeSet(T[] source) {
		return source == null ? null : new TreeSet<>(Arrays.asList(source));
	}
	public static <T> TreeSet<T> collectionToTreeSet(Collection<T> source) {
		return source == null ? null : new TreeSet<>(source);
	}

	public static <T> List<T> arrayToList(T[] source) {
		return arrayToArrayList(source);
	}
	public static <T> List<T> collectionToList(Collection<T> source) {
		return collectionToArrayList(source);
	}

	public static <T> ArrayList<T> arrayToArrayList(T[] source) {
		return source == null ? null : new ArrayList<>(Arrays.asList(source));
	}
	public static <T> ArrayList<T> collectionToArrayList(Collection<T> source) {
		return source == null ? null : new ArrayList<>(source);
	}

	/* Conversions between numbers */
	public static Integer stringToInteger(String str) {
		try {
			return Integer.valueOf(str);
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	public static Integer[] stringToInteger(String[] source) {
		Integer[] toRet = new Integer[source.length];
		for(int i = 0; i < source.length; i++) {
			Integer num = stringToInteger(source[i]);
			if(num == null)		return null;
			toRet[i] = num;
		}
		return toRet;
	}

	public static Long stringToLong(String str) {
		try {
			return Long.valueOf(str);
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	public static Long[] stringToLong(String[] source) {
		Long[] toRet = new Long[source.length];
		for(int i = 0; i < source.length; i++) {
			Long num = stringToLong(source[i]);
			if(num == null)		return null;
			toRet[i] = num;
		}
		return toRet;
	}
	
	public static Double stringToDouble(String str) {
		try {
			return Double.valueOf(str);
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	public static Double[] stringToDouble(String[] source) {
		Double[] toRet = new Double[source.length];
		for(int i = 0; i < source.length; i++) {
			Double num = stringToDouble(source[i]);
			if(num == null)		return null;
			toRet[i] = num;
		}
		return toRet;
	}
	
	public static Float stringToFloat(String str) {
		try {
			return Float.valueOf(str);
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	public static Float[] stringToFloat(String[] source) {
		Float[] toRet = new Float[source.length];
		for(int i = 0; i < source.length; i++) {
			Float num = stringToFloat(source[i]);
			if(num == null)		return null;
			toRet[i] = num;
		}
		return toRet;
	}


	/* Conversion between objects */
	public static Path urlToPath(URL url) {
		try {
			return new File(url.toURI()).toPath();
		} catch (URISyntaxException e) {
			return null;
		}
	}
	public static List<Path> fileToPath(List<File> fileList) {
		if(fileList == null)	return null;
		return fileList.stream().map(File::toPath).collect(Collectors.toList());
	}

	public static List<String> pathToString(List<Path> pathList, boolean absolutePath) {
		if(pathList == null)	return null;
		if(absolutePath) {
			return pathList.stream().map(path -> path.toAbsolutePath().toString()).collect(Collectors.toList());
		} else {
			return pathList.stream().map(Path::toString).collect(Collectors.toList());
		}
	}

	public static Path[] stringToPath(String[] source) {
		Path[] toRet = new Path[source.length];
		for(int i = 0; i < source.length; i++) {
			toRet[i] = Paths.get(source[i]);
		}
		return toRet;
	}

	public static LocalDate stringToLocalDate(String str, DateTimeFormatter formatter) {
		try {
			return LocalDate.parse(str, formatter);
		} catch(DateTimeParseException ex) {
			return null;
		}
	}
	public static LocalTime stringToLocalTime(String str, DateTimeFormatter formatter) {
		try {
			return LocalTime.parse(str, formatter);
		} catch(DateTimeParseException ex) {
			return null;
		}
	}
	public static LocalDateTime stringToLocalDateTime(String str, DateTimeFormatter formatter) {
		try {
			return LocalDateTime.parse(str, formatter);
		} catch(DateTimeParseException ex) {
			return null;
		}
	}
	public static LocalDate[] stringToLocalDate(String[] source, DateTimeFormatter formatter) {
		LocalDate[] toRet = new LocalDate[source.length];
		for(int i = 0; i < source.length; i++) {
			LocalDate parsed = LocalDate.parse(source[i], formatter);
			if(parsed == null) {
				return null;
			}
			toRet[i] = parsed;
		}
		return toRet;
	}
	public static LocalTime[] stringToLocalTime(String[] source, DateTimeFormatter formatter) {
		LocalTime[] toRet = new LocalTime[source.length];
		for(int i = 0; i < source.length; i++) {
			LocalTime parsed = LocalTime.parse(source[i], formatter);
			if(parsed == null) {
				return null;
			}
			toRet[i] = parsed;
		}
		return toRet;
	}
	public static LocalDateTime[] stringToLocalDateTime(String[] source, DateTimeFormatter formatter) {
		LocalDateTime[] toRet = new LocalDateTime[source.length];
		for(int i = 0; i < source.length; i++) {
			LocalDateTime parsed = LocalDateTime.parse(source[i], formatter);
			if(parsed == null) {
				return null;
			}
			toRet[i] = parsed;
		}
		return toRet;
	}

	public static Boolean[] stringToBoolean(String[] source) {
		Boolean[] toRet = new Boolean[source.length];
		for(int i = 0; i < source.length; i++) {
			toRet[i] = Boolean.valueOf(source[i]);
		}
		return toRet;
	}

	public static List<String> stringBuilderToString(List<StringBuilder> sbList) {
		return sbList.stream().map(StringBuilder::toString).collect(Collectors.toList());
	}
	public static List<StringBuilder> stringToStringBuilder(List<String> strList) {
		return strList.stream().map(StringBuilder::new).collect(Collectors.toList());
	}

	public static long localDateToMillis(LocalDate ld) {
		return localDateTimeToMillis(LocalDateTime.of(ld, LocalTime.of(0, 0)));
	}
	public static long localDateTimeToMillis(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	public static LocalDateTime millisToLocalDateTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}
