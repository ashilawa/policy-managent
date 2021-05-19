import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { PolicyService } from '../_services/policy.service';

import { FormGroup, FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPolicy } from '../model/policymodel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  policies?: IPolicy[];
  username: string;
  viewProfileForm!: FormGroup;

  constructor(
    private policyService: PolicyService,
    private tokenStorage: TokenStorageService,
    private modalService: NgbModal,
    private fb: FormBuilder
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
        this.policies = JSON.parse(err.error).message;
      }
    );
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
}
