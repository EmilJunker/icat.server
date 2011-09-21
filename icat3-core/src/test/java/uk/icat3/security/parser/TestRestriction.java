package uk.icat3.security.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import uk.icat3.entity.EntityBaseBean;
import uk.icat3.exceptions.BadParameterException;
import uk.icat3.exceptions.IcatInternalException;

public class TestRestriction {

	private void testGood(List<Token> tokens, String q, String sw, List<String> res, String top)
			throws ParserException, IcatInternalException, BadParameterException {
		Input input = new Input(tokens);
		Restriction e = new Restriction(input);
		assertNull(input.peek(0));
		
		Set<String> relatedEntityNames = new HashSet<String>();
		for (Class<? extends EntityBaseBean> bean : e.getRelatedEntities()) {
			relatedEntityNames.add(bean.getSimpleName());
		}

		assertEquals("Related entities", new HashSet<String>(res), relatedEntityNames);
		assertEquals("SearchWhere", sw, e.getSearchWhere(top));
		assertEquals("Query", q + sw, e.getQuery(top));
	}

	@Test
	public void testGood1() throws Exception {
		List<Token> tokens = Tokenizer
				.getTokens("[id = 20] <-> Investigation <-> Investigator <-> FacilityUser[facilityUserId = :user]");
		String sw = "(Dataset$.id = 20) AND (FacilityUser$.facilityUserId = :user)";
		String q = "SELECT COUNT(Dataset$) FROM Dataset AS Dataset$ LEFT JOIN Dataset$.investigation AS Investigation$ LEFT JOIN Investigation$.investigatorCollection AS Investigator$ LEFT JOIN Investigator$.facilityUser AS FacilityUser$  WHERE (Dataset$.id = :pkid) AND ";
		testGood(tokens, q, sw, Arrays.asList("Investigation", "Investigator", "FacilityUser"), "Dataset");
	}

	@Test
	public void testGood2() throws Exception {
		List<Token> tokens = Tokenizer.getTokens("Investigation Investigator [facilityUser.facilityUserId = :user]");
		String sw = "(Investigator$.facilityUser.facilityUserId = :user)";
		String q = "SELECT COUNT(Dataset$) FROM Dataset AS Dataset$ LEFT JOIN Dataset$.investigation AS Investigation$ LEFT JOIN Investigation$.investigatorCollection AS Investigator$  WHERE (Dataset$.id = :pkid) AND ";
		testGood(tokens, q, sw, Arrays.asList("Investigation", "Investigator"), "Dataset");
	}

	@Test
	public void testGood3() throws Exception {
		List<Token> tokens = Tokenizer.getTokens("Dataset Investigation [invNumber = 'A']");
		String sw = "(Investigation$.invNumber = 'A')";
		String q = "SELECT COUNT(DatasetParameter$) FROM DatasetParameter AS DatasetParameter$ LEFT JOIN DatasetParameter$.dataset AS Dataset$ LEFT JOIN Dataset$.investigation AS Investigation$  WHERE (DatasetParameter$.datasetParameterPK.datasetId = :pkid0 AND DatasetParameter$.datasetParameterPK.name = :pkid1 AND DatasetParameter$.datasetParameterPK.units = :pkid2) AND ";
		Input input = new Input(tokens);
		Restriction e = new Restriction(input);
		assertNull(input.peek(0));
		System.out.println(e.getQuery("DatasetParameter"));
		for (Class<? extends EntityBaseBean> bean : e.getRelatedEntities()) {
			System.out.println(bean.getSimpleName());
		}
		System.out.println(e.getSearchWhere("DatasetParameter"));
		testGood(tokens, q, sw, Arrays.asList("Investigation", "Dataset"), "DatasetParameter");
	}

}