import { Component, OnInit ,ViewChild} from '@angular/core';
import { PolicyService } from '../_services/policy.service';
import {Subject} from 'rxjs';
import {  NgbAlert} from '@ng-bootstrap/ng-bootstrap';
import {debounceTime} from 'rxjs/operators';

@Component({
  selector: 'app-policy',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css'],
})
export class PolicyComponent implements OnInit {
  private _success = new Subject<string>();
  private _failure = new Subject<string>();
  successMessage = '';
  errorMessage='';

  @ViewChild('selfClosingAlert', {static: false}) selfClosingAlert?: NgbAlert;
  policy: any = {
    policyName: '',
    premium: '',
    years: '',
    policyType: '',
    description: '',
    sumAssured:'',
  };

  constructor( private policyService: PolicyService) {}

  ngOnInit(): void {

    this._success.subscribe(message => this.successMessage = message);
    this._success.pipe(debounceTime(5000)).subscribe(() => {
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
    });

    this._failure.subscribe(message => this.errorMessage = message);
    this._failure.pipe(debounceTime(5000)).subscribe(() => {
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
    });
    
  }

  onSubmit() {

    this.policyService.addPolicy(this.policy).subscribe(
      (data) => {
        console.log('data' + data);
        this._success.next("Policy successfully added 😃 ")
      },
      (err) => {
        this._failure.next("Something went wrong 😑  ")
        console.log(err);
      }
    );
    console.log(this.policy);
  }
}
