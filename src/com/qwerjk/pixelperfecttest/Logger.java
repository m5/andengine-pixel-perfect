package com.qwerjk.pixelperfecttest;

import android.util.Log;

public class Logger {
	public static void debug(Object s) {
		if(s != null){
			debug(s.toString());
		} else {
			debug("NULL");
		}
	}

	public static void debug(String s){
		if(s != null) {
			Log.d("qwerjk", s);
		} else {
			debug("NULL");
		}
	}

	public static void trace(Object s){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Stack Trace: %s\n", String.valueOf(s)));
		StackTraceElement[] trace = new RuntimeException().getStackTrace();
		for(int i=1; i<trace.length; i++){
			StackTraceElement el = trace[i];
			builder.append(String.format("\t%s:%d in %s.%s\n", el.getFileName(), el.getLineNumber(), el.getClassName(), el.getMethodName()));
		}
		Logger.log(builder.toString());
	}
	
	public static void f(String format, Object...args){
	    log(String.format(format, args));
	}

	public static void log(Object s){ debug(s); }
	public static void log(String s){ debug(s); }

	public static void log(Object... os){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<os.length; i++){
			builder.append(String.valueOf(os[i]));

			if((i+1) < os.length){
				builder.append(", ");
			}
		}
		log(builder.toString());
	}
	public static void trace(){ trace(""); }
}
