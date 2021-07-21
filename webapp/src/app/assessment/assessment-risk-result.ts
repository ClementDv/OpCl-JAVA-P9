import {RisqueLevel} from "./risque-level";

export class AssessmentRiskResult {
  patientId: number;
  riskLevel: RisqueLevel;
  factorsMatch: string[];

  constructor(obj?: Partial<AssessmentRiskResult>) {
    Object.assign(this, obj);
  }
}
