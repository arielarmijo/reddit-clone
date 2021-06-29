import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupRequest } from '../model/signup-request';
import { Observable } from 'rxjs';
import { LoginRequest } from '../model/login-request';
import { LoginResponse } from '../model/login-response';
import { LocalStorageService } from 'ngx-webstorage';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  signup(signupRequest: SignupRequest): Observable<any> {
    return this.http.post('http://localhost:8080/api/auth/signup', signupRequest, {responseType: 'text'});
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>('http://localhost:8080/api/auth/login', loginRequest).pipe(
      map(data => {
        this.localStorage.store('autheticationToken', data.authenticationToken);
        this.localStorage.store('refreshToken', data.refreshToken);
        return true;
      })
    );
  }

  
}
