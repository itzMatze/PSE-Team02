package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.User;

public class TestActivity extends AppCompatActivity {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        LinearLayout container = findViewById(R.id.container);

        List<Group> dataList = new ArrayList<Group>();

        Group g1 = new Group();
        g1.setName("Gruppe1");
        g1.setMotto("Motto1");

        List<User> userList1 = new ArrayList<User>();
        User u1 = new User();
        u1.setUsername("User1");
        u1.setMotto("Motto1");
        userList1.add(u1);
        User u2 = new User();
        u2.setUsername("User2");
        u2.setMotto("Motto2");
        userList1.add(u2);
        g1.setUsers(userList1);

        dataList.add(g1);
        Group g2 = new Group();
        g2.setName("Gruppe2");
        g2.setMotto("Motto2");
        dataList.add(g2);
        Group g3 = new Group();
        g3.setName("Gruppe3");
        g3.setMotto("Motto3");
        dataList.add(g3);

        GroupList groupList = new GroupList(this, dataList, true);
        container.addView(groupList.getView());
    }
}

/*public class TestActivity extends FragmentActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity_layout);


        if (savedInstanceState == null) {
            MensaMeetList list = new MensaMeetList();
            fragmentTransaction.add(R.id.container, list);
            fragmentTransaction.commit();

        }

    }
}
*/
