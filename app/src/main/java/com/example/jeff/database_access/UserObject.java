package com.example.jeff.database_access;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.aymaan.cse110applogin.Hashing;


public class UserObject {
    private static final String LOG_TAG = "UserO";
    private static void LOG_DEBUG(String msg){
        Log.d(LOG_TAG, msg);
    }

    private long id;
    private String username; //Username is email in most cases
    private String passhash;
//    private String email;
    private boolean email_confirmed;
    private boolean notifications;
    private String name;

//    private boolean exists;
    private boolean up_to_date;


    //Whether or not this user is an active, signed in user or a passive user
    private boolean active;

    private ArrayList<GroupObject> groups;
    private ArrayList<GroupObject> new_groups;

    private ArrayList<EntryObject> entries;
    private ArrayList<EntryObject> new_entries;

    private void assignUserObject(UserObject other){
        id = other.id;
        username = other.username;
        passhash = other.passhash;
        email_confirmed = other.email_confirmed;
        name = other.name;
        up_to_date = other.up_to_date;
        groups = other.groups;
        entries = other.entries;
        active = other.active;
    }


    public UserObject(String username, String passhash){
        this.username = username;
        this.passhash = passhash;
        up_to_date = false;
        active = false;
    }
    private void parseIdFromJson(JSONObject jo){
        id = JsonHelper.parseLong(jo, "id");
    }
    private void parseUsernameFromJson(JSONObject jo){
        username = JsonHelper.parseString(jo, "username");
    }
    private void parseNameFromJson(JSONObject jo){
        name = JsonHelper.parseString(jo, "username");
    }
    private void parsePasshashFromJson(JSONObject jo){
        passhash = JsonHelper.parseString(jo, "passhash");
    }
    private void parseEmailConfirmedFromJson(JSONObject jo){
        email_confirmed = JsonHelper.parseBoolean(jo,"email_confirmed");
    }
    private void parseNotificationsFromJson(JSONObject jo){
        notifications = JsonHelper.parseBoolean(jo,"notifications");
    }


    public UserObject(JSONObject jo, boolean active_user){
        if(active_user){
            assignUserObject(new UserObject(jo));
        } else {
            parseIdFromJson(jo);
            parseUsernameFromJson(jo);
            parseNameFromJson(jo);
        }
    }

    public boolean removeEntryFromLists(EntryObject entry){
        if(entries == null) return false;
        if(groups == null) return false;
        boolean entries_removed = entries.remove(entry);
        for(GroupObject go : groups){
            if(go == null){
                System.err.println("THIS MEANS SOMEONE MESSED UP");
                return false;
            }
            if(go.getEntries().remove(entry))
                return entries_removed;
        }
        return false;
    }


    public UserObject(JSONObject jo){
        if(!jo.has("passhash")){
            assignUserObject(new UserObject(jo, false));
        } else {

            parseIdFromJson(jo);
            parseUsernameFromJson(jo);

            parsePasshashFromJson(jo);
            parseEmailConfirmedFromJson(jo);

            parseNotificationsFromJson(jo);

            parseNameFromJson(jo);

            up_to_date = (id >= 0);

            active = (username != null && passhash != null);
        }

    }

    public long getId() { return id; }

    public String getUsername() { return username; }

    public String getPasshash() { return passhash; }

    public boolean isEmailConfirmed() { return email_confirmed; }

    public boolean isNotifications() { return notifications; }


    // Returns the user's display name and falls back to their email if not defined
    public String getName() {
        return name == null ? ('('+getUsername()+')') :name;
    }

    public String getGroupsString(){
        if(groups == null || groups.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder().append("Groups[");
        boolean first = true;
        for(GroupObject go : groups){
            if(first){
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(go.toString());
        }
        return sb.toString();
    }

    public String toString() {
        return new StringBuilder().append("UserObject(").append(getId())
                .append(", un:").append(getUsername())
                .append(", ph:").append(getPasshash())
                .append(", ec:").append(isEmailConfirmed())
                .append(", in:").append(isNotifications())
                .append(", gn:").append(getName())
                .append(", gs:").append(getGroupsString())
                .append(')').toString();
    }



    public int synchronizeGroups(){
        if(new_groups != null && !new_groups.isEmpty()){
            System.err.println("TODO: IMPLEMENT GROUP PUSHING (this is now handled differently)");
        }

        groups = DatabaseRequest.get_all_groups(this);
        applyUserToGroups();

        return groups.size();
    }

    public int synchronizeEntries(){
        if(new_entries != null && !new_groups.isEmpty()){
            System.err.println("TODO: Implement entry pushing");
        }

        entries = DatabaseRequest.get_all_entries(this);

        //TODO: This is an absolutely terrible way to do this, but it's quick and dirty
        if(entries == null) return 0;
        if(groups == null) return 0;
        for(GroupObject go : groups)
            go.addEntriesCheckGID(entries);



        return entries.size();
    }



    public void synchronize(){
        synchronizeGroups();
        synchronizeEntries();

        LOG_DEBUG("Synchronized user");
        LOG_DEBUG(this.toString());


    }
    public GroupObject createGroup(String group_name){
        GroupObject created_group = DatabaseRequest.create_group(this, group_name);
        if(created_group != null){
            if(groups == null){
                System.err.println("UserObject's groups field should not be null under normal operations");
                System.err.println("\tSomething must be messed up.");
            } else {
                created_group.setUser(this);
                groups.add(created_group);
            }
        } else {
            System.err.println("group creation failed, which probably means there was a server connection or authentication error");
        }

        return created_group;
    }



    public void applyUserToGroups(){
        if(groups == null) return;
        for(GroupObject group : groups)
            group.setUser(this);
    }


    public UserObject createInDatabase(){
        UserObject created = DatabaseRequest.create_user(this);

        if(created == null) return null;

        assignUserObject(created);

        return this;
    }


    public UserObject fetchFromDatabase(){
        UserObject fetched =DatabaseRequest.get_user(getUsername(), getPasshash());

        if(fetched == null) return null;

        assignUserObject(fetched);

        return this;
    }

    public ArrayList<GroupObject> getGroups(){
        return groups;
    }


    // Get a hashmap of entries so you can get an array list of entries for each day
    public Map<String, ArrayList<EntryObject>> getEntryMap(ArrayList<GroupObject> groups){
        if(groups == null) return null;
        Map<String, ArrayList<EntryObject>> entry_map = new HashMap<>();
        for(GroupObject go : groups){
            if(go.getEntries() == null) continue;
            for(EntryObject eo : go.getEntries()){
                String entry_day_str = eo.getDayString();
                ArrayList<EntryObject> day_entries = entry_map.get(entry_day_str);
                if(day_entries == null){
                    day_entries = new ArrayList<>();
                    day_entries.add(eo);
                    entry_map.put(entry_day_str, day_entries);
                } else {
                    day_entries.add(eo);
                }
            }
        }
        return entry_map;
    };

    public Map<String, ArrayList<EntryObject>> getEntryMap(){
        return getEntryMap(groups);
    }

    public boolean isActive(){
        return active;
    }


    public GroupObject joinGroupByEnrollmentCode(String enrollment_code){
        GroupObject joined_group;
        try {
            joined_group = DatabaseRequest.join_group_by_enrollment_code(this, enrollment_code);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        if(joined_group != null) groups.add(joined_group);

        return joined_group;
    }


    public GroupObject getIndividualGroup(){
        if(groups == null) return null;
        for(GroupObject go : groups){
            if(go.isIndividual()) return go;
        }
        return null;
    }


    // Search for groups by name
    public ArrayList<GroupObject> getGroupsMatchingString(String groupName){
        return DatabaseRequest.search_group_name(groupName);
    }

    public boolean create_request(long group_id) throws IOException {
        return DatabaseRequest.create_request(this,group_id);
    }

    public boolean make_request_decision(long request_id, long group_id, boolean decision)
                                         throws IOException{
        return DatabaseRequest.make_request_decision(request_id,this,group_id,decision);
    }

    public boolean leave_group(long user_id, long group_id) throws IOException {
        return DatabaseRequest.leave_group(user_id,group_id);
    }

    public ArrayList<GroupRequestObject> get_requests() {
        return DatabaseRequest.get_requests(this);
    }
    //TODO fix the groupRequestObject









    public boolean changePassword(String current_pass_plaintext, String new_pass_plaintext){
        if(getId() < 0 || getUsername() == null) return false;
        if(current_pass_plaintext == null || new_pass_plaintext == null) return false;


        String current_passhash = null;


        String new_passhash = null;

        try {
            current_passhash = Hashing.SHA1(current_pass_plaintext);
            new_passhash = Hashing.SHA1(new_pass_plaintext);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        if(current_passhash == null || new_passhash == null) return false;


        boolean status = false;

        try {
            status = DatabaseRequest.change_password(getUsername(), current_passhash, new_passhash);
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }

        if(status) this.passhash = new_passhash;

        return status;
    }








    public static boolean ResetPassword(String password){
        boolean status = false;

        try {
            status = DatabaseRequest.reset_password(password);
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        } catch (JSONException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }



    public boolean delete(){
        boolean success = false;

        try {
            success = DatabaseRequest.delete_user(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(success){
            // Make the garbage collector a happiboi
            id = -1;
            username = null;
            passhash = null;
            email_confirmed = false;
            notifications = false;
            name = null;
            up_to_date = false;
            groups = null;
            new_groups = null;
            entries = null;
            new_entries = null;
            return true;
        }
        return false;
    }








//    private boolean updateDatabase(){
//
//
//        return true;
//    }

//    private boolean assign(UserObject other){
//        if(other == null) return false;
////        if(username != other.getUsername()) return false;
//        id = other.getId();
//        username = other.getUsername();
//        passhash = other.getPasshash();
//        email_confirmed = other.isEmailConfirmed();
//        notifications = other.isNotifications();
//        name = other.getName();
//        exists = other.exists;
//        return true;
//    }
//
//    public boolean synchronizeWithDatabase(){
//        UserObject dbUserObject = DatabaseRequest.get_user(getUsername(), getPasshash());
//        if(dbUserObject == null) return false;
//
//        if(this.compareTo(dbUserObject) != 0){
//            return this.assign(dbUserObject);
//        }
//
//        return false;
//    }
//
//    public int compareTo(UserObject other){
//        if(other == null) return Integer.MIN_VALUE;
//        return toString().compareTo(other.toString());
//    }
}
