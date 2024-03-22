package com.app.weatherGPT.service;

import com.app.weatherGPT.model.BotUser;
import com.app.weatherGPT.model.UserCommandCache;
import com.app.weatherGPT.repositories.UserCommandCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandCacheServices {

    private final UserCommandCacheRepository userCommandCacheRepository;
    private final BotUserServices botUserServices;

    public <T> void addCommandCache(User user, String command, String subcommand, T data) {

        BotUser botUser = botUserServices.getUser(user);
        Byte[] array;

        try {
            array = serializeObjectInByte(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserCommandCache commandCache = new UserCommandCache();
        commandCache.setCommand(command);
        commandCache.setSubCommand(subcommand);
        commandCache.setUser(botUser);
        commandCache.setData(array);
        userCommandCacheRepository.save(commandCache);
    }

    public void clearCommandCaches(User user) {

        BotUser botUser = botUserServices.getUser(user);
        List<UserCommandCache> caches = userCommandCacheRepository.findByUser(botUser);

        if (caches.size() > 0) {
            userCommandCacheRepository.deleteAll(caches);
        }
    }

    public Byte[] getCommandCache(User user, String time) {

        BotUser botUser = botUserServices.getUser(user);
        Optional<UserCommandCache> optional = userCommandCacheRepository.findByUserAndSubCommandIgnoreCase(botUser, time);

        return optional.map(UserCommandCache::getData).orElse(null);

    }

    public byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        return bos.toByteArray();
    }

    public Byte[] serializeObjectInByte(Object obj) throws IOException {

        byte[] bos = serializeObject(obj);

        if (bos == null) {
            return null;
        }

        Byte[] bos1 = new Byte[bos.length];

        for (int i = 0; i < bos.length; i++) {
            bos1[i] = bos[i];
        }
        return bos1;
    }


    public <T> T deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
    }

    public <T> T deserializeObject(Byte[] bytes) throws IOException, ClassNotFoundException {

        if (bytes == null) {
            return null;
        }

        byte[] bos1 = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            bos1[i] = bytes[i];
        }

        return (T) deserializeObject(bos1);
    }
}
