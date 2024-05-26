import {Component, OnInit, ViewChild} from '@angular/core';
import {PostService} from "../../service/post.service";
import {Interview} from "../../model/interview.model";
import {MatCalendarCellClassFunction} from "@angular/material/datepicker";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  selected: Date | null = null;
  interviews: Interview[]=[];
  currentInterviews: Interview[] = [];
  dates: Date[]=[];
  isInterview: boolean = false;
  @ViewChild('calendar') calendar: any;

  dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    if (view === 'month') {
      //@ts-ignore
      const date = cellDate.toDate();
      var result = '';
      for(var i=0;i<this.dates.length;i++)
        if(new Date(this.dates[i]).getDate() === new Date(date).getDate()
          && new Date(this.dates[i]).getMonth() === new Date(date).getMonth()
          && new Date(this.dates[i]).getFullYear() === new Date(date).getFullYear())
            result = 'interview-date';
      return result;
    }
    return '';
  };

  constructor(private service: PostService) { }

  ngOnInit(): void {
    this.service.getInterviewsCurrentUser().subscribe(
      (interviews) => {
        this.interviews = interviews;
        this.interviews.forEach(interview => this.dates.push(new Date(interview.interviewTime)));
        this.calendar.updateTodaysDate();
        this.selected = new Date();
        this.checkInterview();
      }
    );
  }

  checkInterview(){
    this.isInterview = false;

    for(let i=0; i<this.dates.length; i++)
      if(new Date(this.dates[i]).getDate() === new Date(this.selected as Date).getDate()
        && new Date(this.dates[i]).getMonth() === new Date(this.selected as Date).getMonth()
        && new Date(this.dates[i]).getFullYear() === new Date(this.selected as Date).getFullYear()) {
          this.isInterview = true;
          break;
      }

    if(this.isInterview){
      this.currentInterviews = [];
      this.interviews.forEach(interview => {
        if(new Date(interview.interviewTime).getDate() === new Date(this.selected as Date).getDate()
          && new Date(interview.interviewTime).getMonth() === new Date(this.selected as Date).getMonth()
          && new Date(interview.interviewTime).getFullYear() === new Date(this.selected as Date).getFullYear()) {
          this.currentInterviews.push(interview);
        }
      });
    }
  }
}
