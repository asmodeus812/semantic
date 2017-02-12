package com.ontotext.semantic.impl.instance;

import java.util.ArrayList;
import java.util.Map;

import org.openrdf.model.Literal;
import org.openrdf.model.Value;

import com.ontotext.semantic.api.common.Converter;
import com.ontotext.semantic.api.enumeration.SemanticInstanceType;
import com.ontotext.semantic.api.instance.Instance;
import com.ontotext.semantic.impl.converter.ByteConverter;
import com.ontotext.semantic.impl.converter.DoubleConverter;
import com.ontotext.semantic.impl.converter.FloatConverter;
import com.ontotext.semantic.impl.converter.IntegerConverter;
import com.ontotext.semantic.impl.converter.ShortConverter;
import com.ontotext.semantic.impl.converter.StringConverter;

/**
 * Represents a semantic literal or atomic value that can not be sub classed
 * 
 * @author Svetlozar
 */
public class SemanticLiteral implements Instance {

	/**
	 * Complete literal chain converter supporting all basic types - byte, short, integer, float, double, string
	 */
	public static final Converter<Literal> LITERAL_CONVERTER = new ByteConverter(
			new ShortConverter(
					new IntegerConverter(
							new FloatConverter(
									new DoubleConverter(
											new StringConverter())))));

	private Literal literal;

	/**
	 * Initializes a semantic literal from a given value
	 * 
	 * @param literal
	 *            the atomic value or a literal
	 */
	public SemanticLiteral(Value literal) {
		this.literal = (Literal) literal;
	}

	/**
	 * Initializes a semantic literal from a given value and a data type
	 * 
	 * @param atomicValue
	 *            the atomic value or a literal
	 */
	public SemanticLiteral(String value) {
		this.literal = LITERAL_CONVERTER.convert(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Literal getInstanceValue() {
		return literal;
	}

	@Override
	public Map<Instance, ArrayList<Instance>> getPropertyMap() {
		// A literal can not have any properties
		return null;
	}

	@Override
	public void insertProperty(Instance property, Instance value) {
		// No properties can be inserted for a literal
	}

	@Override
	public void removeProperty(Instance property) {
		// No properties can be removed for a literal
	}

	@Override
	public void modifyProperty(Instance property, Instance oldValue, Instance newValue) {
		// No properties can be modified for a literal
	}

	@Override
	public String toString() {
		return literal.getLabel();
	}

	@Override
	public int hashCode() {
		return literal.getLabel().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SemanticLiteral)) {
			return false;
		}

		SemanticLiteral other = (SemanticLiteral) obj;
		return literal.getLabel().equals(other.literal.getLabel());
	}

	@Override
	public SemanticInstanceType getInstanceType() {
		return SemanticInstanceType.LITERAL;
	}

}
