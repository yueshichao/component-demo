package com.lsz.info.push.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class DateAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonWriter.value(sdf.format(date));
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return deserializeToDate(jsonReader.nextString());
    }

    private Date deserializeToDate(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(s);
        } catch (Exception e) {
            log.error("parse date error, {}", s, e);
        }
        return null;
    }

}
