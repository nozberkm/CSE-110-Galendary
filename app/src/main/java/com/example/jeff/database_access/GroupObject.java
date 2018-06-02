package com.example.jeff.database_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GroupObject {
    private long id = -1;
    private String name = null;
    private String enrollment_code = null;
    private boolean is_public = false;
    private boolean looking_for_subgroups = false;
    private boolean individual = false;
    private boolean admin = false;

    private UserObject user;


    private ArrayList<EntryObject> entry_list;
    private ArrayList<EntryObject> new_entries;

    public GroupObject(int id, String name){
        this.id = id;
        this.name = name;
        admin = true;
        user = null;
    }

    public GroupObject(JSONObject jo){
        try {
            id = jo.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = jo.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jo.has("enrollment_code")) {
            try {
                enrollment_code = jo.isNull("enrollment_code") ? null : jo.getString("enrollment_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jo.has("is_public")) {
            try {
                is_public = jo.getInt("is_public") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            is_public = false;
        }
        if(jo.has("looking_for_subgroups")) {
            try {
                looking_for_subgroups = jo.getInt("looking_for_subgroups") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            looking_for_subgroups = false;
        }
        if(jo.has("individual")) {
            try {
                individual = jo.getInt("individual") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            individual = false;
        }

        if(jo.has("admin")) {
            try {
                admin = jo.getInt("admin") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        user = null;

    }

    public UserObject getUser() { return user; }

    public void setUser(UserObject user) {
        this.user = user;
        if(entry_list == null) return;
        for(EntryObject entry : entry_list)
            entry.setUser(user);
    }

    public long getId() { return id; }

    public String getName() { return name; }

    public String getEnrollmentCode() { return enrollment_code; }

    public boolean isPublic() { return is_public; }

    public boolean isLookingForSubgroups() { return looking_for_subgroups; }

    public boolean isIndividual() { return individual; }

    public String toString(){
        return "Group(" + getId() + ", " + getName() + ", " + (entry_list ==null ? 0 : entry_list.size()) + ')';
    }

    public boolean isAdmin(){ return admin; }



    public EntryObject pushEntry(EntryObject entry){
        if(getUser() == null){
            System.err.println("attempting to create entry in group with null user");
            return null;
        }
        EntryObject result = DatabaseRequest.create_entry(getUser(), this, entry);
        if(result == null){
            System.err.println("Failed to create entry");
        }

        return result;
    }

    public EntryObject addEntry(EntryObject eo){
        if (eo != null) {
            if (entry_list == null) entry_list = new ArrayList<>();
            if (eo.getGroupId() < 0 && eo.getId() < 0) {
                // Pass the entry object on to the creator to make it in the database and modify eo

                System.err.println("TODO: Make this not improper use of addEntry");
                eo = null;//pushEntry(eo);
                // TODO: Make the group object take a UserObject field to simplify this. This must be skipped for now
            } else if(eo.getGroupId() < 0 || eo.getId() < 0){
                System.err.println("An entry object was initialized in a way that breaks the specification. " +
                        "Discarding it instead of adding it to entries.");
            }
            if(eo != null) entry_list.add(eo);
        }
        return eo;
    }

    public int addEntryCheckGID(EntryObject eo){
        if(entry_list == null) entry_list = new ArrayList<>();
        if(eo.getGroupId() == getId()){
            if(addEntry(eo) != null)
                return 1;
        }
        return 0;
    }

    public int addEntriesCheckGID(ArrayList<EntryObject> entry_list){
        int add_count = 0;
        for(EntryObject eo : entry_list)
            add_count += addEntryCheckGID(eo);
        return add_count;
    }




//    public EntryObject pushEntry(EntryObject entry){
//
//
//        return null;
//    }





    public boolean setPublicFlag(boolean public_flag){
        if(getUser() == null){
            System.err.println("attempting to set public flag for group with null user");
            return false;
        }
        if(!isAdmin()) return false;
        if(public_flag == isPublic()) return true;

        if(DatabaseRequest.alter_group_set_public_flag(user, this, public_flag)){
            this.is_public = public_flag;
            return true;
        }
        return false;
    }
    public boolean makePublic(){
        return setPublicFlag(true);
    }
    public boolean makePrivate(){
        return setPublicFlag(false);
    }

    public boolean setLookingForSubgroups(boolean looking_for_subgroups){
        if(getUser() == null){
            System.err.println("attempting to set looking flag for group with null user");
            return false;
        }
        if(!isAdmin()) return false;
        if(isLookingForSubgroups() == looking_for_subgroups) return true;
        if(DatabaseRequest.alter_group_set_looking_flag(getUser(), this, looking_for_subgroups)){
            this.looking_for_subgroups = looking_for_subgroups;
            return true;
        }
        return false;
    }

    public boolean changeName(String new_name){
        if(getUser() == null){
            System.err.println("Attempting to change name of group that has null user");
            return false;
        }
        if(!isAdmin() || new_name == null) return false;
        if(new_name.compareTo(getName()) == 0) return true;

        if(DatabaseRequest.alter_group_change_name(getUser(), this, new_name)){
            this.name = new_name;
            return true;
        }
        return false;
    }

    public void setEnrollmentCode(String enrollment_code){
        this.enrollment_code = enrollment_code;
    }


    public String generateEnrollmentCode(){
        if(!isAdmin() || DatabaseRequest.generate_enrollment_code(this) == null)
            return null;
        return getEnrollmentCode();
    }


    public static ArrayList<GroupObject> searchGroupsByName(String group_name){

        ArrayList<GroupObject> group_list = DatabaseRequest.search_group_name(group_name);


        return group_list;
    }


    public ArrayList<EntryObject> getEntries(){
        return entry_list;
    }


    public ArrayList<GroupObject> getRelatedGroups(){
        ArrayList<GroupObject> toret = null;
        try {
            toret = DatabaseRequest.get_related_groups(this);
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("GROUP", "Failed to get related groups, server issue possible");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toret;
    }


}
