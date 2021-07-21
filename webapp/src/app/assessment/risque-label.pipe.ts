import {Pipe, PipeTransform} from '@angular/core';
import {RisqueLevel} from "./risque-level";

@Pipe({
  name: 'risqueLabel'
})
export class RisqueLabelPipe implements PipeTransform {

  transform(value: RisqueLevel): string {
    const translations = {
      NONE: 'No risk',
      BORDERLINE: 'Borderline risk',
      IN_DANGER: 'Patient in danger',
      EARLY_ONSET: 'Early onset'
    };
    return translations[value];
  }

}
