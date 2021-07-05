import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IPolicy } from '../model/policymodel';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root',
})
export class PolicyService {

  API_URL = '';

  constructor(private http: HttpClient) {

    this.API_URL = environment.policyApi;
  }

  addPolicy(policy: IPolicy): Observable<any> {
    return this.http.post(this.API_URL + 'save', policy,{ responseType: 'text' });
  }


  updatePolicy(policy: IPolicy): Observable<any> {
    let params = new HttpParams().set('policyId', policy.policyId.toString());
    return this.http.put(this.API_URL + 'update', policy, {
      params,
    });
  }

  getUserPolicy(username: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this.http.get(this.API_URL + 'user', { responseType: 'text', params });
  }

  registerUserPolicy(username: string, policyId: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this.http.get(this.API_URL+ 'register/'+ username + '/' + policyId , { responseType: 'text', params });
  }

  deRegisterUserPolicy(username: string, policyId: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this.http.get(this.API_URL+ 'deregister/'+ username + '/' + policyId , { responseType: 'text', params });
  }

  deletePolicy(policyId: string): Observable<any> {
    let params = new HttpParams().set('policyId', policyId);
    return this.http.delete(this.API_URL + 'delete', {
      responseType: 'text',
      params,
    });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(this.API_URL + 'all/active', { responseType: 'text' });
  }
}
