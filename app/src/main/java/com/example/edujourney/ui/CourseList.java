package com.example.edujourney.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edujourney.R;
import com.example.edujourney.adapter.CourseAdapter;
import com.example.edujourney.db.CourseDataSource;
import com.example.edujourney.db.ProfileDataSource;
import com.example.edujourney.model.Course;
import com.example.edujourney.model.Profile;

import java.util.ArrayList;
import java.util.Comparator;

public class CourseList extends AppCompatActivity {

    private ProfileDataSource profileDataSource;
    private CourseDataSource coursesDataSource;

    CourseAdapter courseAdapter;

    private Profile profile;

    private ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        profileDataSource = new ProfileDataSource(this);
        coursesDataSource = new CourseDataSource(this);

        if (savedInstanceState != null) {
            profile = (Profile) savedInstanceState.getSerializable("PROFILE_OBJECT");
        } else {
            profile = profileDataSource.getProfile();
        }



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("PROFILE_OBJECT", profile);
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter = new CourseAdapter(courses);
        recyclerView.setAdapter(courseAdapter);
    }



    @Override
    protected void onResume() {
        super.onResume();

        if (profile == null) {
            profile = (Profile) getIntent().getSerializableExtra("PROFILE_OBJECT");
        }

        setProfileTextViews();

       courses = coursesDataSource.getCoursesForProfile(profile.getProfileID());
       initializeRecyclerView();
       initializeSortButton();
    }

    private void initializeSortButton() {

        ImageButton sortButton = findViewById(R.id.sortCurseItemImageButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CourseList.this, sortButton);
                popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.option_date_asc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c1.getCreationDate().compareTo(c2.getCreationDate());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if(itemId == R.id.option_title_asc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c1.getCourseTitle().compareTo(c2.getCourseTitle());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_semester_asc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return Integer.compare(c1.getCourseSemester(), c2.getCourseSemester());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_date_desc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c2.getCreationDate().compareTo(c1.getCreationDate());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if(itemId == R.id.option_title_desc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c2.getCourseTitle().compareTo(c1.getCourseTitle());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_semester_desc) {
                            courses.sort(new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return Integer.compare(c2.getCourseSemester(), c1.getCourseSemester());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    protected void onDestroy() {

        profileDataSource.close();
        coursesDataSource.close();
        super.onDestroy();
    }

    public void changeProfileButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditProfile.class);
        intent.putExtra("MODE", "EDIT");
        intent.putExtra("PROFILE_OBJECT", profile);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slide_right_in, R.anim.animation_slide_left_in);
    }

    public void addCourseButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditCourse.class);
        intent.putExtra("MODE", "NEW");
        intent.putExtra("PROFILE_OBJECT", profile);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slide_right_in, R.anim.animation_slide_left_in);
    }

    private void setProfileTextViews() {

        TextView userFullNameTextView = findViewById(R.id.userFullNameTextView);
        TextView userStudyProgramTextView = findViewById(R.id.userStudyProgramTextView);

        String fullName = profile.getFirstName() + " " + profile.getLastName();
        userFullNameTextView.setText(this.getString(R.string.name_dp, fullName));
        userStudyProgramTextView.setText(this.getString(R.string.study_program_dp, profile.getStudyProgram()));
    }

}