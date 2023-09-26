package client;

import com.mongodb.*;

import java.net.UnknownHostException;


public class MongoDB {
    public static MongoClient mongoClient;
    public static DB dataBase;
    public static DBCollection test;

    public static void main(String[] args) throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27002"));
        dataBase = mongoClient.getDB("projectGroupSeventeen");
        test = dataBase.getCollection("testing");
//        DBObject object = new BasicDBObject("user","champion").append("hignscore","10000").append("table","panj");
        TestObj obj = new TestObj();
        obj.setMemberId("456");
        obj.setTimer(456);
        obj.setXp(789);
        System.out.println(convert(obj));
       /* test.insert(convert(obj));
        DBObject query = new BasicDBObject("Xp", 789);
        DBCursor cursor = test.find(query);
        //bara inke avalisho bede age chand ta bud
        System.out.println(cursor.one());
        //bara inke meghdare yekio bedim be yeki dige
        TestObj obj2 = new TestObj();
        obj.setMemberId("9630");
        obj.setTimer(1010);
        obj.setXp(2020);
        test.findAndModify(query,convert(obj2));
        cursor = test.find(query);
        System.out.println(cursor.one());
*/
    }

    public static DBObject convert(TestObj testObj){
        return new BasicDBObject("Xp", testObj.getXp()).append("Timer",testObj.getTimer()).append("memberID",testObj.getMemberId());
    }


























}

