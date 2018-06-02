package com.example.jeff.database_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseRequest {
    public static boolean verify_login(String username, String passhash) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "verify_login"},
                {"username", username},
                {"passhash", passhash}
        });

        JSONObject jo = GalendaryDB.server_request(pb);

        try {
            return (boolean) jo.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UserObject get_user(String username, String passhash) {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_user"},
                {"username", username},
                {"passhash", passhash}
        });

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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "create_user"},
                {"username", username},
                {"passhash", passhash}
        });

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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "alter_group"},
                {"username", username},
                {"passhash", passhash},
                {"group_id", String.valueOf(group_id)},
                {"is_public", String.valueOf(is_public)}
        });

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affected_rows")) {
            try {
                return jo.getInt("affected_rows") == 1;
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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "alter_group"},
                {"username", username},
                {"passhash", passhash},
                {"group_id", String.valueOf(group_id)},
                {"looking_for_subgroups", String.valueOf(looking_for_subgroups)}
        });

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affected_rows")) {
            try {
                return jo.getInt("affected_rows") == 1;
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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "alter_group"},
                {"username", username},
                {"passhash", passhash},
                {"group_id", String.valueOf(group_id)},
                {"name", new_name}
        });

        JSONObject jo = null;
        try {
            jo = GalendaryDB.server_request(pb);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (jo.has("err")) return false;
        if (jo.has("affected_rows")) {
            try {
                return jo.getInt("affected_rows") == 1;
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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_all_groups"},
                {"username", username},
                {"passhash", passhash}
        });
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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_all_entries"},
                {"username", username},
                {"passhash", passhash}
        });
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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "search_group_name"},
                {"group_name", group_name}
        });

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
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "create_group"},
                {"username", username},
                {"passhash", passhash},
                {"group_name", group_name}
        });

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

    public static boolean create_request(String username, String passhash, String group_name) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "create_request"},
                {"username", username},
                {"passhash", passhash},
                {"group_name", group_name}
        });

        //How do we return
        JSONObject jo = GalendaryDB.server_request(pb);
        return !jo.has("err");
    }

    public static boolean create_request(UserObject user) {
        return false;
        // TODO: What is String group_name?
        // return create_request(user.getUsername(),user.getPasshash(), String group_name);
    }

    public static boolean make_request_decision(String request_username,
                                                String request_passhash,
                                                String admin_username,
                                                String admin_passhash,
                                                String group_name,
                                                boolean accepted) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "respond_request"},
                {"request_username", request_username},
                {"request_passhash", request_passhash},
                {"admin_username", admin_username},
                {"admin_passhash", admin_passhash},
                {"group_name", group_name},
                {"decision", accepted ? "true" : "false"}
        });

        JSONObject jo = GalendaryDB.server_request(pb);
        return !jo.has("err");
    }

    public static boolean make_request_decision(String username,
                                                String passhash,
                                                UserObject admin,
                                                String group_name,
                                                boolean accepted) {
        return false;
//        TODO: Figure out how to make this call without causing a compiler error
//        return make_request_decision(username,passhash,admin.getUsername(),
//                                     admin.getUsername(),accepted);
    }

    public static ArrayList<GroupRequestObject> get_requests(String username,
                                                             String passhash) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_requests"},
                {"username", username},
                {"passhash", passhash}
        });

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
            // TODO: How to handle if this throws an exception?
            e.printStackTrace();
        }
        return new ArrayList<>(); // TODO: Correct this incorrect handling
    }

    public static boolean change_password(String username, String passhash, String passhash_new) throws IOException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "change_password"},
                {"username", username},
                {"passhash", passhash},
                {"passhash_new", passhash_new}
        });

        JSONObject jo = GalendaryDB.server_request(pb);

        System.out.println(jo.toString());

        if (jo.has("data")) {
            try {
                return (Integer) jo.getJSONArray("data").getJSONArray(0).getJSONObject(0).get("success") != 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            System.out.println(jo2.toString());
        }

        return false;
//        !jo.has("err");
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
                System.err.println("I don't know why this happened. Sorry m8");
                return null;
            }
            entry.applyGroupAsOwner(group, eid);
            return entry;
        }


        return null;
    }

    public static String get_enrollment_code(String username, String passhash, long group_id) {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_enrollment_code"},
                {"username", username},
                {"passhash", passhash}
        });
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
    public static String get_enrollment_code(UserObject user, GroupObject group){
        if(user == null || group == null) return null;
        return get_enrollment_code(user.getUsername(), user.getPasshash(), group.getId());
    }
    public static String get_enrollment_code(GroupObject group){
        if(group == null) return null;
        return get_enrollment_code(group.getUser(), group);
    }



    public static String generate_enrollment_code(String username, String passhash, long group_id) {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "generate_enrollment_code"},
                {"username", username},
                {"passhash", passhash}
        });
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
        if(user == null || group == null) return null;
        if(!group.isAdmin()) return null;

        String enrollment_code = generate_enrollment_code(user.getUsername(), user.getPasshash(), group.getId());
        if (enrollment_code != null) {
            group.setEnrollmentCode(enrollment_code);
            return group;
        }
        return null;
    }
    public static GroupObject generate_enrollment_code(GroupObject group){
        if(group.getUser() == null) return null;
        return generate_enrollment_code(group.getUser(), group);
    }
    public static ArrayList<GroupObject> get_related_groups(String username, String passhash, long group_id) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
                {"command", "get_related_groups"},
                {"username", username},
                {"passhash", passhash}
        });
        pb.push("group_id", group_id);


        JSONObject jo = GalendaryDB.server_request(pb);


        if(!jo.has("data")) return null;
        JSONArray data = jo.getJSONArray("data");

        ArrayList<GroupObject> related = new ArrayList<>();
        for(int i=0; i<data.length(); ++i){
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
/*
    public static ArrayList<String> get_admin_email(long group_id) {
        ParameterBuilder pb = new ParameterBuilder(new String[][]{
            {"command","get_admin_email"},
        });
        pb.push("group_id", group_id);
        
        JSONObject jo = GalendaryDB.server_request(pb);

        if (!jo.has("data")) return null;
        JSONArray data = jo.getJSONArray("data");

        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            String curr_email = data.getJSONObject(i);
            emails.add(curr_email);
        }

        return emails;
    }
*/

    public static boolean add_group_to_related(String username, String passhash, long id_group_a, long id_group_b) throws IOException, JSONException {
        ParameterBuilder pb = new ParameterBuilder("add_group_to_related");
        pb.push_username(username)
            .push_passhash(passhash)
            .push("group_id_a", id_group_a)
            .push("group_id_b", id_group_b);

        JSONObject jo = GalendaryDB.server_request(pb);

        return (jo.has("affected_rows") && jo.getInt("affected_rows") > 0);

    }
    public static boolean add_group_to_related(UserObject user, GroupObject group_a, GroupObject group_b) throws IOException, JSONException {
        return add_group_to_related(user.getUsername(), user.getPasshash(), group_a.getId(), group_b.getId());
    }

        // Admin of group_a, member of groupb
    public static boolean add_group_to_related(GroupObject group_a, GroupObject group_b) throws IOException, JSONException {
        return add_group_to_related(group_a.getUser(), group_a, group_b);
    }
}
