package com.bsod.tfg.utils;

import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Proudly created by Payton on 30/11/2014.
 */
public class MessageBoardDeserializer extends JsonDeserializer<MessageBoard> {
    /**
     * Method that can be called to ask implementation to deserialize
     * JSON content into the value type this serializer handles.
     * Returned instance is to be constructed by method itself.
     * <p>
     * Pre-condition for this method is that the parser points to the
     * first event that is part of value to deserializer (and which
     * is never JSON 'null' literal, more on this below): for simple
     * types it may be the only value; and for structured types the
     * Object start marker or a FIELD_NAME.
     * </p>
     * <p>
     * The two possible input conditions for structured types result
     * from polymorphism via fields. In the ordinary case, Jackson
     * calls this method when it has encountered an OBJECT_START,
     * and the method implementation must advance to the next token to
     * see the first field name. If the application configures
     * polymorphism via a field, then the object looks like the following.
     * <pre>
     *      {
     *          "@class": "class name",
     *          ...
     *      }
     *  </pre>
     * Jackson consumes the two tokens (the <tt>@class</tt> field name
     * and its value) in order to learn the class and select the deserializer.
     * Thus, the stream is pointing to the FIELD_NAME for the first field
     * after the @class. Thus, if you want your method to work correctly
     * both with and without polymorphism, you must begin your method with:
     * <pre>
     *       if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
     *         jp.nextToken();
     *       }
     *  </pre>
     * This results in the stream pointing to the field name, so that
     * the two conditions align.
     * </p>
     *
     * Post-condition is that the parser will point to the last
     * event that is part of deserialized value (or in case deserialization
     * fails, event that was not recognized or usable, which may be
     * the same event as the one it pointed to upon call).
     *
     * Note that this method is never called for JSON null literal,
     * and thus deserializers need (and should) not check for it.
     *
     * @param jp   Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *             this deserialization activity.
     * @return Deserialized value
     */
    @Override
    public MessageBoard deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {


        JsonNode node = jp.getCodec().readTree(jp);
        MessageBoard mb = new MessageBoard();

        mb.setId((Integer) (node.get("pk")).numberValue());
        mb.setMessage(node.get("texto").asText());
        mb.setCreationDateUnix(node.get("fecha_creacion").asLong());
        mb.setNumOfFavs((Integer) node.get("num_fav").numberValue());
        mb.setUserFavorited((node.get("user_favorited").booleanValue()));
        mb.setOwner(node.get("owner").booleanValue());


        int idUser = (Integer) node.get("usuario").get("pk").numberValue();
        User u;
        if (Session.getSession().getMapUsers().containsKey(idUser)) {
            u = Session.getSession().getMapUsers().get(idUser);
        } else {
            String name = node.get("usuario").get("username").asText();
            String image = node.get("usuario").get("image").asText();
            u = new User();
            u.setIdUser(idUser);
            u.setName(name);
            u.setPicImageUrl(image);
            Session.getSession().getMapUsers().put(idUser, u);
        }

        mb.setUser(u);


        return mb;
    }
}
