import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IPolicy } from '../model/policymodel';

const API_URL = 'http://localhost:8080/api/policy/';

@Injectable({
  providedIn: 'root',
})
export class PolicyService {
  constructor(private http: HttpClient) {}

  addPolicy(policy: IPolicy): Observable<any> {
    return this.http.post(API_URL + 'save', policy,{ responseType: 'text' });
  }


  updatePolicy(policy: IPolicy): Observable<any> {
    let params = new HttpParams().set('policyId', policy.policyId.toString());
    return this.http.put(API_URL + 'update', policy, {
      params,
    });
  }

  getUserPolicy(username: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this.http.get(API_URL + 'user', { responseType: 'text', params });
  }

  registerUserPolicy(username: string, policyId: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this.http.get(API_URL+ 'register/'+ username + '/' + policyId , { responseType: 'text', params });
  }

  deletePolicy(policyId: string): Observable<any> {
    let params = new HttpParams().set('policyId', policyId);
    return this.http.delete(API_URL + 'delete', {
      responseType: 'text',
      params,
    });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'all/active', { responseType: 'text' });
  }
}
