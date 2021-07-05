import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


//const this.AUTH_API = 'http://localhost:8080/policy-management-server/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  environmentName = '';
  AUTH_API = '';

  constructor(private http: HttpClient) {
    this.environmentName = environment.environmentName;
    this.AUTH_API = environment.authApi;
  }

  test(): Observable<any> {
    return this.http.get(this.AUTH_API + 'test',  httpOptions);
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(this.AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(this.AUTH_API + 'signup', {
      username,
      email,
      password
    }, httpOptions);
  }
}
