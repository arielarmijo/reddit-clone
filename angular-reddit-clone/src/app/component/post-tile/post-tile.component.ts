import { Component, Input, OnInit } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { Post } from 'src/app/model/post';
import { PostService } from 'src/app/service/post.service';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

  faComments = faComments;

  posts: Array<Post> = [];

  constructor(private postService: PostService) {
    this.postService.getAllPosts().subscribe(posts => {
      this.posts = posts;
    });
  }

  ngOnInit(): void {
  }

}
