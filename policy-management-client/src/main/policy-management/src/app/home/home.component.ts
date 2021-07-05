import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { PolicyService } from '../_services/policy.service';

import { FormGroup, FormBuilder } from '@angular/forms';
import { NgbAlert, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPolicy } from '../model/policymodel';
import { debounceTime } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  policies: IPolicy[] = [];
  username: string;
  viewProfileForm!: FormGroup;
  policy?: IPolicy;
  private _success = new Subject<string>();
  successMessage = '';
  errorMessage = '';
  deRegisterIndex?: number;

  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert?: NgbAlert;


  constructor(
    private policyService: PolicyService,
    private tokenStorage: TokenStorageService,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private router: Router,
  ) {
    this.username = this.tokenStorage.getUser().username;
  }

  ngOnInit(): void {
    this.viewProfileForm = this.fb.group({
      policyId: [{ value: '', disabled: true }],
      policyName: [{ value: '', disabled: true }],
      premium: [{ value: '', disabled: true }],
      sumAssured: [{ value: '', disabled: true }],
      years: [{ value: '', disabled: true }],
      policyType: [{ value: '', disabled: true }],
      description: [{ value: '', disabled: true }],
    });

    this.policyService.getUserPolicy(this.username).subscribe(
      (data) => {
        this.policies = JSON.parse(data);
        console.log(this.policies);
      },
      (err) => {
        console.log(err.status == 0);
       
      }
    );

    this._success.subscribe(message => this.successMessage = message);
    this._success.pipe(debounceTime(5000)).subscribe(() => {
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
    });
  }
  openModal(targetModal: any, policy: IPolicy) {
    this.modalService.open(targetModal, {
      centered: true,
    });
    this.viewProfileForm.patchValue({
      policyId: policy.policyId,
      policyName: policy.policyName,
      premium: policy.premium,
      sumAssured: policy.sumAssured,
      years: policy.years,
      policyType: policy.policyType,
      description: policy.description,
    });
  }

  deletePolicy(policy: IPolicy) {
    console.log('delete' + policy.policyId);
    this.policyService.deRegisterUserPolicy(this.username, policy.policyId.toString()).subscribe(
      (data) => {
        console.log('data' + data);
        this._success.next("Policy successfully deregistered 😃")
        //this.reloadPage();    
        if (this.policies) {
          this.policies.forEach((pol, index) => {
            if (pol.policyId == policy.policyId) this.policies.splice(index, 1);
          });
        }
      },
      (err) => {
        this.errorMessage = "Something went wrong 😑  ";

        console.log(err);
      }
    );
  }

  deRegisterModal(targetModal: any, policy: IPolicy) {
    this.modalService.open(targetModal, {
    });
    this.policy = policy;

  }

  onDelete() {
    this.modalService.dismissAll();
    if (this.policy) this.deletePolicy(this.policy);
  }

  reloadPage(): void {
    window.location.reload();
  }
}
