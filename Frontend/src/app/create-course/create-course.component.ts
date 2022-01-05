import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseTopic } from '../model/courseTopic.model';
import { CourseService } from '../service/course.service';
import { CourseTopicService } from '../service/courseTopic.service';
import { TopicService } from '../service/topic.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.css'],
})
export class CreateCourseComponent implements OnInit {
  obj = {
    teacherName: "",
    courseName: '',
    description: '',
    startOfCourses: new Date(),
    endOfCourses: new Date(),
  };
  
  teachers!: string[];
  startOfCourses = '';
  endOfCourses = '';
  selectTeacher: string = "Выберите преподавателя";
  message!: string;
  errorDescription!: string;

  topicPattern!: string;
  selectTopic: string = 'Выберите тему занятия';
  loadTopic!: string[];
  preAdd!: string;
  addedTopic: string[] = [];
  err: string = '';
  newTopic!: string;
  createMessage: string = '';

  courseTopicList: CourseTopic[] = [];
  errorCreateNewTopic = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private courseService: CourseService,
    private topicService: TopicService,
    private courseTopicService: CourseTopicService
  ) { }


  ngOnInit(): void {
    this.getTeacher();
  }

  deleteTopic(topicName: string) {
    this.addedTopic.forEach((item, index) => {
      if (item === topicName) this.addedTopic.splice(index, 1);
    });
  }

  getTeacher() {
    this.userService.getTeacher().subscribe((data) => {
      console.log(data);

      this.teachers = data;
    });
  }

  onChangeDateStart(date: Date) {

    this.startOfCourses = date.toString();
  }

  onChangeDateEnd(date: Date) {

    this.endOfCourses = date.toString();
  }

  onChangeCourse(form: NgForm) {
    this.message = '';
    this.errorDescription = '';

    if (
      form.invalid || this.addedTopic.length ==0 || this.startOfCourses=='' || this.endOfCourses==''
    ) {

      for (const control of Object.keys(form.controls)) {
        form.controls[control].markAsTouched();
      }

      this.message = 'error';
      this.errorDescription = 'not all options are selected';

    } else {

      const course2: Course = {
        courseName: this.obj.courseName,
        description: this.obj.description,
        teacherName: this.obj.teacherName,
        startOfCourses: this.startOfCourses,
        endOfCourses: this.endOfCourses
      }

      this.courseService.addCourse(course2).subscribe((data) => {
        console.log(data);
        this.message = data;

        for (let top of this.addedTopic) {
          this.createCourseTopic(top, this.obj.courseName);
        }

        this.courseTopicService
          .addCourseTopics(this.courseTopicList).subscribe((data) => {
            console.log(data);
          });
      },
			(err: any) => {
			  console.log(err);
			  //this.error = err;

        this.message = 'error';
        this.errorDescription = 'Course with this course name "' + this.obj.courseName + '" already exists';

			});

    }
  }

  createNewTopic() {
    this.errorCreateNewTopic = '';
    
    this.topicService.createTopic(this.newTopic).subscribe((data) => {
      console.log(data);
      this.createMessage = data;
      setTimeout(() => {
        this.createMessage = '';
      }, 5000);
      this.preAdd = this.newTopic;
      this.addTopic();
    }, () => {
      this.errorCreateNewTopic = 'topic already exists';
    });
  }

  createCourseTopic(topicName: string, courseName: string) {
    const courseTopic: CourseTopic = {
      courseName: courseName,
      topicName: topicName,
    };

    this.courseTopicList.push(courseTopic);
  }

  onChangePattern(patter: string) {
    this.topicService.getTopicByPattern(patter).subscribe((data) => {
      console.log(data);

      this.loadTopic = data;
      this.selectTopic = 'Выберите тему занятия';
    });
  }

  onChangeTopic(topic: string) {
    if (topic != 'Выберите тему занятия') {
      this.preAdd = topic;
    }
  }

  addTopic() {
    let flag: boolean = false;
    if (this.addedTopic.length == 0) {
      console.log(this.preAdd);
      this.addedTopic.push(this.preAdd);
    } else {
      for (let t of this.addedTopic) {
        if (t === this.preAdd) {
          flag = true;
        }
      }

      if (flag) {
        this.err = 'this topic already exists';
      } else {
        this.addedTopic.push(this.preAdd);
        this.err = '';
      }

    }
  }
}
