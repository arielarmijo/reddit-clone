import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subreddit } from '../model/subreddit';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {

  constructor(private http: HttpClient) { }

  getAllSubreddits(): Observable<Array<Subreddit>> {
    return this.http.get<Array<Subreddit>>('http://localhost:8080/api/subreddits/');
  }

}
