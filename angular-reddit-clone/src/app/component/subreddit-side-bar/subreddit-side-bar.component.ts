import { Component, OnInit } from '@angular/core';
import { Subreddit } from 'src/app/model/subreddit';
import { SubredditService } from 'src/app/service/subreddit.service';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {

  subreddits: Array<Subreddit> = [];
  displayViewAll = false;

  constructor(private subredditService: SubredditService) {
    this.subredditService.getAllSubreddits().subscribe(subreddits => {
      if (subreddits.length >= 4) {
        this.subreddits = subreddits.splice(0,3);
        this.displayViewAll = true;
      } else {
        this.subreddits = subreddits;
      }
    });
   }

  ngOnInit(): void {
  }

}
