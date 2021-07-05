import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUser } from '../model/usermodel';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  AUTH_API = '';

  constructor(private http: HttpClient) {

    this.AUTH_API = environment.userApi;
   }

  getAlluser(): Observable<any> {
    return this.http.get(this.AUTH_API + 'user/all', { responseType: 'text' });
  }
 

  getAllrole(): Observable<any> {
    return this.http.get(this.AUTH_API + 'role/all', { responseType: 'text' });
  }

  

  updateUser(user: IUser): Observable<any> {
    return this.http.put(this.AUTH_API + 'user/update', user);
  }
}
