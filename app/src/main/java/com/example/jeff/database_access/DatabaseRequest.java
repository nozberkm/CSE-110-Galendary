package com.example.jeff.database_access;

import android.support.v4.util.Pair;

import com.example.aymaan.cse110applogin.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseRequest {
    public static boolean verify_login(String username, String passhash) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("verify_login");
        pb.push_username(username);
        pb.push_passhash(passhash);

        JSONObject jo = GalendaryDB.server_request(pb);

        try {
            return (boolean) jo.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UserObject get_user(String username, String passhash) {
        ParameterBuilder pb = new ParameterBuilder("get_user");
        pb.push_username(username);
        pb.push_passhash(passhash);


        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jo == null || !jo.has("data")) {
            return null;
        }

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (data.length() != 1) return null;

        UserObject user = null;
        try {
            user = new UserObject(data.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }


    public static UserObject create_user(String username, String passhash) {
        ParameterBuilder pb = new ParameterBuilder("create_user");
        pb.push_username(username);
        pb.push_passhash(passhash);


        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jo == null || jo.has("err")) {
            return null;
        }

        return get_user(username, passhash);
    }

    public static UserObject create_user(UserObject new_user) {
        UserObject created_user = create_user(new_user.getUsername(), new_user.getPasshash());
        return created_user;
    }






    /* Methods for altering an already existing group's properties. Requires a group id and that the accessing
        user be an admin of that group.
     */

    public static boolean alter_group_set_public_flag(String username, String passhash, long group_id, boolean is_public) {
        ParameterBuilder pb = new ParameterBuilder("alter_group");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);
        pb.push("is_public", is_public);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affectedRows")) {
            try {
                return jo.getInt("affectedRows") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean alter_group_set_public_flag(UserObject user, long group_id, boolean is_public) {
        return alter_group_set_public_flag(user.getUsername(), user.getPasshash(), group_id, is_public);
    }

    public static boolean alter_group_set_public_flag(UserObject user, GroupObject group, boolean is_public) {
        return alter_group_set_public_flag(user, group.getId(), is_public);
    }


    public static boolean alter_group_set_looking_flag(String username, String passhash, long group_id, boolean looking_for_subgroups) {
        ParameterBuilder pb = new ParameterBuilder("alter_group");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);
        pb.push("looking_for_subgroups", looking_for_subgroups);


        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affectedRows")) {
            try {
                return jo.getInt("affectedRows") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean alter_group_set_looking_flag(UserObject user, long group_id, boolean looking_for_subgroups) {
        return alter_group_set_looking_flag(user.getUsername(), user.getPasshash(), group_id, looking_for_subgroups);
    }

    public static boolean alter_group_set_looking_flag(UserObject user, GroupObject group, boolean looking_for_subgroups) {
        return alter_group_set_looking_flag(user, group.getId(), looking_for_subgroups);
    }

    public static boolean alter_group_change_name(String username, String passhash, long group_id, String new_name) {
        ParameterBuilder pb = new ParameterBuilder("alter_group");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);
        pb.push("group_name", new_name);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affectedRows")) {
            try {
                return jo.getInt("affectedRows") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean alter_group_change_name(UserObject user, long group_id, String new_name) {
        return alter_group_change_name(user.getUsername(), user.getPasshash(), group_id, new_name);
    }

    public static boolean alter_group_change_name(UserObject user, GroupObject group, String new_name) {
        return alter_group_change_name(user, group.getId(), new_name);
    }


    public static ArrayList<GroupObject> get_all_groups(String username, String passhash) {
        ParameterBuilder pb = new ParameterBuilder("get_all_groups");
        pb.push_username(username);
        pb.push_passhash(passhash);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!jo.has("data") || jo.isNull("data")) return null;

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() == 0) return null;

        ArrayList<GroupObject> group_list = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                group_list.add(new GroupObject(data.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return group_list;
    }

    public static ArrayList<GroupObject> get_all_groups(UserObject user) {
        return get_all_groups(user.getUsername(), user.getPasshash());
    }

    public static ArrayList<EntryObject> get_all_entries(String username, String passhash) {
        ParameterBuilder pb = new ParameterBuilder("get_all_entries");
        pb.push_username(username);
        pb.push_passhash(passhash);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!jo.has("data") || jo.isNull("data")) return null;

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() == 0) return null;

        ArrayList<EntryObject> entry_list = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                entry_list.add(new EntryObject(data.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return entry_list;
    }

    public static ArrayList<EntryObject> get_all_entries(UserObject user) {
        return get_all_entries(user.getUsername(), user.getPasshash());
    }

    public static ArrayList<GroupObject> search_group_name(String group_name) {
        ParameterBuilder pb = new ParameterBuilder("search_group_name");
        pb.push("group_name", group_name);

        JSONObject jo = null;

        try {
            jo = GalendaryDB.server_request(pb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!jo.has("data") || jo.isNull("data")) return null;

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() == 0) return null;

        ArrayList<GroupObject> group_list = new ArrayList<GroupObject>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                group_list.add(new GroupObject(data.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return group_list;
    }


    public static GroupObject create_group(String username, String passhash, String group_name) {
        ParameterBuilder pb = new ParameterBuilder("create_group");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_name", group_name);

        JSONObject jo;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (jo.has("data")) {
            JSONArray data = null;
            try {
                data = jo.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (data.length() > 0) {
                int gid = 0;
                try {
                    gid = data.getJSONArray(0).getJSONObject(0).getInt("gid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return new GroupObject(gid, group_name);
            }
        }
        return null;
    }

    public static GroupObject create_group(UserObject user, String group_name) {
        return create_group(user.getUsername(), user.getPasshash(), group_name);
    }

    public static boolean create_request(String username, String passhash, long group_id) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("create_request");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);

        //How do we return
        JSONObject jo = GalendaryDB.server_request(pb);
        return !jo.has("err");
    }

    public static boolean create_request(UserObject user, long group_id) throws IOException {
        //return false;
        return create_request(user.getUsername(), user.getPasshash(), group_id);
    }

    public static boolean make_request_decision(long request_id,
                                                String admin_username,
                                                String admin_passhash,
                                                long group_id,
                                                boolean accepted) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "respond_request"},
                {"admin_username", admin_username},
                {"admin_passhash", admin_passhash},
                {"decision", accepted ? "true" : "false"}
        });
        pb.push("group_id", group_id);
        pb.push("request_id", request_id);
        JSONObject jo = GalendaryDB.server_request(pb);
        return !jo.has("err");
    }

    public static boolean make_request_decision(long request_id,
                                                UserObject admin,
                                                long group_id,
                                                boolean accepted) throws IOException {
        //      return false;
        return make_request_decision(request_id, admin.getUsername(), admin.getPasshash(),
                group_id, accepted);
    }

    public static ArrayList<GroupRequestObject> get_requests(String username,
                                                             String passhash) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("get_requests");
        pb.push_username(username);
        pb.push_passhash(passhash);


        JSONObject jo = null;

        try {
            jo = GalendaryDB.server_request(pb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!jo.has("data") || jo.isNull("data")) return null;

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() == 0) return null;

        ArrayList<GroupRequestObject> request_list = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                request_list.add(new GroupRequestObject(data.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return request_list;
    }

    public static ArrayList<GroupRequestObject> get_requests(UserObject user) {
        try {
            return get_requests(user.getUsername(), user.getPasshash());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean change_password(String username, String passhash, String passhash_new) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("change_password");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("passhash_new", passhash_new);

        JSONObject jo = GalendaryDB.server_request(pb);

        if (jo.has("data")) {
            try {
                return (Integer) jo.getJSONArray("data").getJSONArray(0).getJSONObject(0).get("success") != 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    // modifies the passed EntryObject
    public static EntryObject create_entry(UserObject user, GroupObject group, EntryObject entry) {
        ParameterBuilder pb = new ParameterBuilder(user);
        pb.push("command", "create_entry");
        pb.push("group_id", group.getId());
        pb.push("title", entry.getTitle());
        pb.push("start_time", entry.getStart());
        pb.push("end_time", entry.getEnd());
        pb.push("recurrence", entry.getRecurrence());
        pb.push("priority", entry.getPriority());
        pb.push("description", entry.getDescription());

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ja.length() > 0) {
            long eid = -1;
            try {
                eid = ja.getJSONArray(0).getJSONObject(0).getLong("entry_id");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("This means more than one thing is horribly wrong. Sorry m8");
                return null;
            }
            entry.applyGroupAsOwner(group, eid);
            return entry;
        }


        return null;
    }

    public static String get_enrollment_code(String username, String passhash, long group_id) {
        ParameterBuilder pb = new ParameterBuilder("get_enrollment_code");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JSONArray ja = null;
        try {
            ja = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        if (ja.length() == 1) {
            JSONObject code_object = null;
            try {
                code_object = ja.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (code_object.has("enrollment_code")) {
                if (code_object.isNull("enrollment_code"))
                    return null;
                try {
                    return code_object.getString("enrollment_code");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public static String get_enrollment_code(UserObject user, GroupObject group) {
        if (user == null || group == null) return null;
        return get_enrollment_code(user.getUsername(), user.getPasshash(), group.getId());
    }

    public static String get_enrollment_code(GroupObject group) {
        if (group == null) return null;
        return get_enrollment_code(group.getUser(), group);
    }


    public static String generate_enrollment_code(String username, String passhash, long group_id) {
        ParameterBuilder pb = new ParameterBuilder("generate_enrollment_code");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (jo.has("enrollment_code")) {
            try {
                if (!jo.isNull("enrollment_code"))
                    return jo.getString("enrollment_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static GroupObject generate_enrollment_code(UserObject user, GroupObject group) {
        if (user == null || group == null) return null;
        if (!group.isAdmin()) return null;

        String enrollment_code = generate_enrollment_code(user.getUsername(), user.getPasshash(), group.getId());
        if (enrollment_code != null) {
            group.setEnrollmentCode(enrollment_code);
            return group;
        }
        return null;
    }

    public static GroupObject generate_enrollment_code(GroupObject group) {
        if (group.getUser() == null) return null;
        return generate_enrollment_code(group.getUser(), group);
    }

    public static ArrayList<GroupObject> get_related_groups(String username, String passhash, long group_id) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("get_related_groups");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);


        JSONObject jo = GalendaryDB.server_request(pb);


        if (!jo.has("data")) return null;
        JSONArray data = jo.getJSONArray("data");

        ArrayList<GroupObject> related = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            JSONObject gjo = data.getJSONObject(i);
            related.add(new GroupObject(gjo));
        }

        return related;
    }

    public static ArrayList<GroupObject> get_related_groups(UserObject user, GroupObject group) throws IOException, JSONException {
        return get_related_groups(user.getUsername(), user.getPasshash(), group.getId());
    }

    public static ArrayList<GroupObject> get_related_groups(GroupObject group) throws IOException, JSONException {
        return get_related_groups(group.getUser(), group);
    }

    public static ArrayList<String> get_admin_email(long group_id) throws JSONException, IOException {
        ParameterBuilder pb = new ParameterBuilder("get_admin_email");
        pb.push("group_id", group_id);

        JSONObject jo = GalendaryDB.server_request(pb);

        if (!jo.has("data")) return null;
        JSONArray data = jo.getJSONArray("data");

        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            String curr_email = data.getJSONObject(i).getString("admin_email");
            emails.add(curr_email);
        }

        return emails;
    }


    public static boolean dissolve_group(String username, String passhash, long group_id)
            throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "dissolve_group"}
        });
        pb.push("group_id", group_id);
        pb.push_username(username);
        pb.push_passhash(passhash);


        JSONObject jo = GalendaryDB.server_request(pb);
//{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}
        if (!jo.has("affectedRows")) return false;

        if(jo.getInt("affectedRows") == 1) return true;

        return false;
    }

    public static boolean dissolve_group(UserObject user, long group_id) throws IOException, JSONException {
        return dissolve_group(user.getUsername(), user.getPasshash(), group_id);
    }







    public static boolean add_group_to_related(String username, String passhash, long id_group_a, long id_group_b) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("add_group_to_related");
        pb.push_username(username)
                .push_passhash(passhash)
                .push("group_id_a", id_group_a)
                .push("group_id_b", id_group_b);

        JSONObject jo = GalendaryDB.server_request(pb);

        return (jo.has("affectedRows") && jo.getInt("affectedRows") > 0);

    }

    public static boolean add_group_to_related(UserObject user, GroupObject group_a, GroupObject group_b) throws IOException, JSONException {
        return add_group_to_related(user.getUsername(), user.getPasshash(), group_a.getId(), group_b.getId());
    }

    // Admin of group_a, member of groupb
    public static boolean add_group_to_related(GroupObject group_a, GroupObject group_b) throws IOException, JSONException {
        return add_group_to_related(group_a.getUser(), group_a, group_b);
    }


    public static GroupObject join_group_by_enrollment_code(String username, String passhash, String enrollment_code) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("join_group_by_enrollment_code");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("enrollment_code", enrollment_code);

        JSONObject jo = GalendaryDB.server_request(pb);
//[[{"id":15,"name":"test_Group","enrollment_code":"pd9fnlp","is_public":0,"looking_for_subgroups":0,"individual":0}],{"fieldCount":0,"affectedRows":0,"insertId":0,"serverStatus":34,"warningCount":0,"message":"","protocol41":true,"changedRows":0}]

        if (!jo.has("data")) return null;
        JSONArray ja = jo.getJSONArray("data");
        if (ja.length() != 2) {
            throw new IOException("IDK SOMEONE MESSED SOMETHING UP");
        }

        JSONArray ja2 = ja.getJSONArray(0);

        if (ja2.length() != 1) return null;
        JSONObject group_jo = ja2.getJSONObject(0);

        return new GroupObject(group_jo);
    }

    public static GroupObject join_group_by_enrollment_code(UserObject user, String enrollment_code) throws IOException, JSONException {
        return join_group_by_enrollment_code(user.getUsername(), user.getPasshash(), enrollment_code);
    }

    public static ArrayList<UserObject> load_group_members(GroupObject group) throws IOException, JSONException {
        // TODO: Handle security issue by requiring username and passhash
        // So that only members of the group have access to the group members
        ParameterBuilder pb = new ParameterBuilder("load_group_members");
        pb.push("group_id", group.getId());

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!jo.has("data") || jo.isNull("data")) return null;

        JSONArray data = null;
        try {
            data = jo.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.length() == 0) return null;

        ArrayList<UserObject> user_list = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                user_list.add(new UserObject(data.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user_list;
    }


    // Pair of User's id and entryObject
    public static ArrayList<Pair<Long, EntryObject>> get_group_member_entries(String username, String passhash, long group_id) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("get_events_of_group_members");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("group_id", group_id);

        JSONObject jo = GalendaryDB.server_request(pb);

        if (!jo.has("data")) return null;
        JSONArray data = jo.getJSONArray("data");
        JSONArray ja = data.getJSONArray(0);


        ArrayList<Pair<Long, EntryObject>> toret = new ArrayList<>();
        for (int i = 0; i < ja.length(); ++i) {
            JSONObject entry_jo = ja.getJSONObject(i);
            EntryObject entry = new EntryObject(entry_jo);
            long uid = entry_jo.getLong("uid");
            toret.add(new Pair<Long, EntryObject>(uid, entry));
        }

        return toret;

    }

    public static ArrayList<Pair<Long, EntryObject>> get_group_member_entries(UserObject user, GroupObject group) throws IOException, JSONException {
        return get_group_member_entries(user.getUsername(), user.getPasshash(), group.getId());
    }

    public static ArrayList<Pair<Long, EntryObject>> get_group_member_entries(GroupObject group) throws IOException, JSONException {
        return get_group_member_entries(group.getUser(), group);
    }


    public static boolean delete_entry(String username, String passhash, long entry_id) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("delete_entry");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("entry_id", entry_id);

        JSONObject jo = GalendaryDB.server_request(pb);

        return jo.has("affectedRows") && jo.getInt("affectedRows") == 1;
    }

    public static boolean delete_entry(EntryObject entry) throws IOException, JSONException {
        UserObject user = entry.getUser();
        return delete_entry(user.getUsername(), user.getPasshash(), entry.getId());
    }

    public static String get_group_name(long group_id) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("get_group_name");
        pb.push("group_id", group_id);

        JSONObject jo = null;

        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject nameObject = null;
        JSONArray data = null;

        try {
            if (!jo.has("data") || jo.isNull("data")) return null;
            data = jo.getJSONArray("data");
            nameObject = data.getJSONObject(0);
        } catch (JSONException e) {
            return null;
        }


        if (nameObject.has("name")) {
            try {
                if (!nameObject.isNull("name"))
                    return nameObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static EntryObject update_entry(EntryObject eo) throws IOException, JSONException {
        System.err.println("Entered update_entry:");
        if (eo == null) return null;
        System.err.println(1);
        if (eo.getId() < 0) return null;
        System.err.println(2);


        // For some reason, the entry object's user field is not being set if it's in an indiviudal group
        UserObject user = eo.getUser();
        System.err.println(3);
        if (user == null){
            // Apply a ridiculously shitty patch. Pls no hate me java gods
            user = LoginActivity.userLogin;
            if(user == null){
                System.err.println("THE BED HAS BEEN SHIT IN");
                return null;
            }
        }
        System.err.println(4);
        if (eo.getGroupId() < 0) return null;
        System.err.println(5);


        ParameterBuilder pb = new ParameterBuilder("update_entry");

        pb.push_username(user.getUsername());
        pb.push_passhash(user.getPasshash());
        pb.push("entry_id", eo.getId());
        pb.push("title", eo.getTitle());
        pb.push("start_time", eo.getStart());
        pb.push("end_time", eo.getEnd());
        pb.push("recurrence", eo.getRecurrence());
        pb.push("priority", eo.getPriority());
        pb.push("description", eo.getDescription());

        JSONObject jo = GalendaryDB.server_request(pb);
        if (!jo.has("data")) return null;
        JSONArray ja = jo.getJSONArray("data");
        JSONArray ja2 = ja.getJSONArray(0);
        JSONObject jo2 = ja2.getJSONObject(0);
        if (!jo2.has("success")) return null;

        System.err.println(6);

        return jo2.getInt("success") == 1 ? eo : null;
//                [[{"success":1}],{"fieldCount":0,"affectedRows":0,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}]
//                [[{"success":0}],{"fieldCount":0,"affectedRows":0,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}]

    }

    public static boolean promote_to_admin(long user_id, long group_id) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("promote_to_admin");
        pb.push("user_id", user_id);
        pb.push("group_id", group_id);

        JSONObject jo = GalendaryDB.server_request(pb);
        if (!jo.has("data")) return false;
        else return true;
    }

    public static boolean leave_group(long user_id, long group_id) throws IOException {
        ParameterBuilder pb = new ParameterBuilder("leave_group");
        pb.push("user_id", user_id);
        pb.push("group_id", group_id);

        JSONObject jo = null;

        jo = GalendaryDB.server_request(pb);
        if (!jo.has("err")) return true;
        else return false;
    }


    // Returns true if password was successfully reset
    public static boolean reset_password(String username) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("reset_password");
        pb.push_username(username);


        JSONObject jo = GalendaryDB.server_request(pb);
        if (!jo.has("success")) return false;

        return (jo.getBoolean("success"));
        //{"success":true}
    }


    public static boolean delete_user(String username, String passhash) throws IOException, JSONException {
//        System.err.println("Attempting to call delete_user: for " + username + "|" + passhash);
        ParameterBuilder pb = new ParameterBuilder("delete_user");
        pb.push_username(username);
        pb.push_passhash(passhash);

        JSONObject jo = GalendaryDB.server_request(pb);
        ///{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}
//        System.err.println("jo:");
//        System.err.println(jo);

        if (!jo.has("affectedRows")) return false;
        return (jo.getInt("affectedRows") == 0);
    }

    public static boolean delete_user(UserObject user) throws IOException, JSONException {
        return delete_user(user.getUsername(), user.getPasshash());
    }


    public static boolean change_user_display_name(String username, String passhash, String display_name) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("change_user_display_name");
        pb.push_username(username);
        pb.push_passhash(passhash);
        pb.push("display_name", display_name);

        JSONObject jo = GalendaryDB.server_request(pb);
//        {"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"(Rows matched: 1  Changed: 1  Warnings: 0","protocol41":true,"changedRows":1}
        if (!jo.has("affectedRows")) return false;

        return jo.getInt("affectedRows") == 1;
    }

    public static boolean change_user_display_name(UserObject user, String display_name) throws IOException, JSONException {
        if (user == null) return false; // Probably not necessary to check this
        return change_user_display_name(user.getUsername(), user.getPasshash(), display_name);
    }
}

